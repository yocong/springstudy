package com.study.springstudy.springmvc.chap05.api;

import com.study.springstudy.springmvc.chap05.dto.request.LoginDto;
import com.study.springstudy.springmvc.chap05.dto.request.SignUpDto;
import com.study.springstudy.springmvc.chap05.dto.response.LoginUserInfoDto;
import com.study.springstudy.springmvc.chap05.service.LoginResult;
import com.study.springstudy.springmvc.chap05.service.MemberService;
import com.study.springstudy.springmvc.util.FileUtil;
import com.study.springstudy.springmvc.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/members")
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    @Value("${file.upload.root-path}")
    private String rootPath;


    private final MemberService memberService;

    //회원가입 양식 열기
    // /members로 똑같으면 String -> void, return 생략해도 사이트 연결됨
    @GetMapping("/sign-up")
//    @ResponseBody // 이거 붙이면 jsp가 아니라 그냥 txt가 날라감
    public void signUp() {
        log.info("/members/sign-up GET : forwarding to sign-up.jsp");
//        return "members/sign-up";
    }

    // 회원가입 요청 처리
    @PostMapping("/sign-up")
    public String signUp(@Validated SignUpDto dto) {


        log.info("/members/sign-up POST ");
        log.debug("parameter: {}", dto);

        // 프로필 사진 추출
        MultipartFile profileImage = dto.getProfileImage();

        String profilePath = null;
        if (!profileImage.isEmpty()) {
            log.debug("attached profile image name: {}", profileImage.getOriginalFilename());
            // 서버에 업로드 후 업로드 경로 반환
            profilePath = FileUtil.uploadFile(rootPath, profileImage);
        }

        boolean flag = memberService.join(dto, profilePath);

        return flag ? "redirect:/members/sign-in" : "redirect:/members/sign-up";
    }


    // 아이디, 이메일 중복검사 비동기 요청 처리
    @GetMapping("/check")
    @ResponseBody // 비동기이므로
    public ResponseEntity<?> check(String type, String keyword) {
        boolean flag = memberService.checkIdentifier(type, keyword);
        log.debug("{} 중복체크 결과? {}", type, flag);
        return ResponseEntity
                .ok()
                .body(flag);
    }

    // 로그인 양식 열기
    // @RequestParam(required = false) String redirect
    // -> 사용자가 로그인한 후에 가고 싶어하는 페이지 주소 (이 요청이 필수는 아니야!)
    @GetMapping("/sign-in")
    public String signIn(HttpSession session
                        , @RequestParam(required = false) String redirect)
    {

        // redirect를 session에 저장 (로그인 후에 어디 갈지 기억함)
        session.setAttribute("redirect", redirect);

        log.info("/members/sign-in GET : forwarding to sign-in.jsp");
        return "members/sign-in";
    }

    // 로그인 요청 처리
    @PostMapping("/sign-in")
    public String signIn(LoginDto dto,
                         RedirectAttributes ra,
                         HttpServletRequest request,
                         HttpServletResponse response) {
        log.info("/members/sign-in POST"); // 로그에 "로그인 요청이 들어왔어요"라고 기록
        // LoginDto에 @Setter 없으면 에러! (로그의 중요성) 꼭 찍어보자
        log.debug("parameter: {}", dto); // 사용자가 입력한 로그인 정보를 로그에 기록

        // 세션 얻기
        HttpSession session = request.getSession();

        LoginResult result = memberService.authenticate(dto, session, response);

        // 로그인 검증 결과를 수송객체를 통해 JSP에게 보내기
        // Redirect시에  Redirect된 페이지에 데이터를 보낼 떄는
        // Model객체를 사용할 수 없음
        // 왜냐면 Model 객체는 request 객체를 사용하는데 해당 객체는
        // 한 번의 요청이 끝나면 메모리에서 제거된다. 그러나 redirect는
        // 요청이 2번 발생하므로 다른 request객체를 jsp가 사용하게 됨
//        model.addAttribute("result", result); // (X)
        ra.addFlashAttribute("result", result);

        if(result == LoginResult.SUCCESS) {

            // 혹시 세션에 저장된 리다이렉트 URL이 있다면
            String redirect = (String) session.getAttribute("redirect");
            if (redirect != null) {
                session.removeAttribute("redirect");
                return "redirect:" + redirect;
            }

            return "redirect:/index"; // 로그인 성공시
        }

        return "redirect:/members/sign-in"; // 로그인 실패시 다시 제자리
    }

    @GetMapping("/sign-out")
    public String signOut(HttpServletRequest request, HttpServletResponse response) {
        // 세션구하기
        HttpSession session = request.getSession();

        // 자동로그인 상태인지 확인
        if (LoginUtil.isAutoLogin(request)) {
            // 쿠키를 제거하고, DB에도 자동로그인 관련데이터를 원래대로 해놓음
            memberService.autoLoginClear(request, response);
        }

        // 세션에서 로그인 기록 삭제
        // LoginUtil에서 로그인에 대한 정보는 "login"에 있기 떄문에
        session.removeAttribute("login");
        // 세션을 초기화
        session.invalidate();
        // 홈으로 보내기
        return "redirect:/";
    }
}
