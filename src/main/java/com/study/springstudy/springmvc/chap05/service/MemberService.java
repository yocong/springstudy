package com.study.springstudy.springmvc.chap05.service;

import com.study.springstudy.springmvc.chap05.dto.request.LoginDto;
import com.study.springstudy.springmvc.chap05.dto.request.SignUpDto;
import com.study.springstudy.springmvc.chap05.entity.Member;
import com.study.springstudy.springmvc.chap05.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.study.springstudy.springmvc.chap05.service.LoginResult.*;

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
    public LoginResult authenticate(LoginDto dto) {

        // 회원가입 여부 확인
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

        log.info("{}님 로그인 성공", foundMember.getName());
        return SUCCESS;
    }

    // 아이디, 이메일 중복검사
    public boolean checkIdentifier(String type, String keyword) {
        return memberMapper.existsById(type, keyword);
    }
}
