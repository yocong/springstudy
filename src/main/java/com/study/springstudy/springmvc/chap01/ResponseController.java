package com.study.springstudy.springmvc.chap01;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

// 응답 처리
@Controller
public class ResponseController {

    // JSP파일로 포워딩할 때 데이터 전달하기
    // 1. Model 객체 사용하기
    @RequestMapping("/hobbies")
    public String hobbies(Model model) {

        model.addAttribute("name", "김철수");
        model.addAttribute("hobbies", List.of("축구", "수영", "영화보기"));

        model.addAttribute("major", "경영학");

        return "mvc/hobbies";
    }

    // 2. ModelAndView 객체 사용하기
    @RequestMapping("/hobbies2")
    public ModelAndView hobbies2() {
        ModelAndView mv = new ModelAndView("mvc/hobbies");

        mv.addObject("name", "박영희");
        mv.addObject("hobbies", List.of("멍때리기", "맛집가기"));
        mv.addObject("major", "컴퓨터 과학");

        return mv;
    }


}
