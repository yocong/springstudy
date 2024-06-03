package com.study.springstudy.springmvc.chap05.service;

import com.study.springstudy.springmvc.chap05.dto.request.AutoLoginDto;
import com.study.springstudy.springmvc.chap05.dto.request.LoginDto;
import com.study.springstudy.springmvc.chap05.dto.request.SignUpDto;
import com.study.springstudy.springmvc.chap05.dto.response.LoginUserInfoDto;
import com.study.springstudy.springmvc.chap05.entity.Member;
import com.study.springstudy.springmvc.chap05.mapper.MemberMapper;
import com.study.springstudy.springmvc.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.time.LocalDateTime;

import static com.study.springstudy.springmvc.chap05.service.LoginResult.*;
import static com.study.springstudy.springmvc.util.LoginUtil.LOGIN;

@Service
@RequiredArgsConstructor // 의존 객체 주입
@Slf4j
public class MemberService {

    // 의존 객체
    private final MemberMapper memberMapper;
    private final PasswordEncoder encoder;

    // 회원 가입 중간 처리
    public boolean join(SignUpDto dto) {
        // 1. dto -> 엔터티로 변환
        Member member = dto.toEntity();

        // 2. 비밀번호를 인코딩 (암호화)
        String encodedPassword = encoder.encode(dto.getPassword());
        member.setPassword(encodedPassword); // 인코딩된 비밀번호를 setter를 통해 다시 보냄

        return memberMapper.save(member);
    }

    // 로그인 검증 처리
    public LoginResult authenticate(LoginDto dto, HttpSession session, HttpServletResponse response) {

        // 회원의 아이디를 통해 회원가입 여부 확인 (findOne)
        String account = dto.getAccount();
        Member foundMember = memberMapper.findOne(account);

        // 회원가입이 안되었을 때
        if (foundMember == null) {
            log.info("{} = 회원가입이 필요합니다.", account);
            return NO_ACC;
        }

        // 비밀번호 일치 검사
        String inputPassword = dto.getPassword(); // 클라이언트에 입력한 비밀번호
        String originPassword = foundMember.getPassword(); // DB에 저장된 비밀번호

        // PasswordEncoder에서는 암호화된 비번을 내부적으로 비교해주는 기능을 제공
        // 확인해주는 기능만 제공
        if (!encoder.matches(inputPassword, originPassword)) {
            log.info("비밀번호가 일치하지 않습니다.");
            return NO_PW;
        }

        // 자동로그인 추가 처리
        // autoLogin이 true인지 확인
        if (dto.isAutoLogin()) {
            // 1. 자동 로그인 쿠키 생성
            // - 쿠키 내부에 절대로 중복되지 않는 유니크한 값을 저장
            //   (UUID, SessionID)
            String sessionid = session.getId();
            Cookie autoLoginCookie = new Cookie(LoginUtil.AUTO_LOGIN_COOKIE, sessionid);
            // 쿠키 설정
            autoLoginCookie.setPath("/"); // 쿠키를 사용할 경로 (전체)
            autoLoginCookie.setMaxAge(60 * 60 * 24 * 90); // 자동로그인 유지 시간

            // 2. 쿠키를 클라이언트에 전송 - 응답바디에 실어보내야 함
            response.addCookie(autoLoginCookie);

            // 3. DB에도 해당 쿠키값을 저장
            memberMapper.updateAutoLogin(
                    AutoLoginDto.builder()
                            .sessionId(sessionid)
                            .limitTime(LocalDateTime.now().plusDays(90))
                            .account(account)
                            .build());
        }

        log.info("{}님 로그인 성공", foundMember.getName());

        // 세션의 수명 : 설정된 시간 OR 브라우저를 닫기 전까지
        int maxInactiveInterval = session.getMaxInactiveInterval();
        session.setMaxInactiveInterval(60 * 60); // 세션 수명 1시간 설정
        log.debug("sessiontime: {}", maxInactiveInterval);

        // 로그인에 성공했을 때 클라이언트에 보낼 정보를 LoginUserInfoDto 객체에 담아서
        // 로그인한 사용자의 정보를 세션에 저장! (비밀번호는 제외)
        session.setAttribute(LOGIN, new LoginUserInfoDto(foundMember));

        return SUCCESS;
    }

    // 아이디, 이메일 중복검사
    public boolean checkIdentifier(String type, String keyword) {
        return memberMapper.existsById(type, keyword);
    }
}
