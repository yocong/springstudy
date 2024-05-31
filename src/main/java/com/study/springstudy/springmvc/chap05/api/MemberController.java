package com.study.springstudy.springmvc.chap05.api;

import com.study.springstudy.springmvc.chap05.dto.request.LoginDto;
import com.study.springstudy.springmvc.chap05.dto.request.SignUpDto;
import com.study.springstudy.springmvc.chap05.service.LoginResult;
import com.study.springstudy.springmvc.chap05.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/members")
@Slf4j
@RequiredArgsConstructor
public class MemberController {

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
        log.info("/members/sign-up POST");
        log.debug("parameter: {}", dto);

        boolean flag = memberService.join(dto);
        // 회원가입 성공? -> 로그인 페이지로 이동
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
    @GetMapping("/sign-in")
    public void signIn() {
        log.info("/members/sign-in GET : forwarding to sign-in.jsp");
    }

    // 로그인 요청 처리
    @PostMapping("/sign-in")
    public String signIn(LoginDto dto, RedirectAttributes ra) {
        log.info("/members/sign-in POST");
        // LoginDto에 @Setter 없으면 에러! (로그의 중요성) 꼭 찍어보자
        log.debug("parameter: {}", dto);

        LoginResult result = memberService.authenticate(dto);

        // 로그인 검증 결과를 수송객체를 통해 JSP에게 보내기
        // Redirect시에  Redirect된 페이지에 데이터를 보낼 떄는
        // Model객체를 사용할 수 없음
        // 왜냐면 Model 객체는 request 객체를 사용하는데 해당 객체는
        // 한 번의 요청이 끝나면 메모리에서 제거된다. 그러나 redirect는
        // 요청이 2번 발생하므로 다른 request객체를 jsp가 사용하게 됨
//        model.addAttribute("result", result); // (X)
        ra.addFlashAttribute("result", result);

        if(result == LoginResult.SUCCESS) {
            return "redirect:/index"; // 로그인 성공시
        }

        return "redirect:/members/sign-in"; // 로그인 실패시 다시 제자리
    }
}
