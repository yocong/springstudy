package com.study.springstudy.webservlet.chap02.v3.controller;

import com.study.springstudy.webservlet.MemberMemoryRepo;
import com.study.springstudy.webservlet.ModelAndView;
import com.study.springstudy.webservlet.View;
import com.study.springstudy.webservlet.chap02.v2.controller.ControllerV2;
import com.study.springstudy.webservlet.entity.Member;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ShowController implements ControllerV3 {

    private MemberMemoryRepo repo = MemberMemoryRepo.getInstance();

    @Override
    public ModelAndView process(Map<String, String> paramMap) {

        List<Member> memberList = repo.findAll();

        // v1에서 봤듯이 수송객체에서 담아서 forward해주는 역할을 ShowController에서 하는데
        // 수송객체에서 담는 것을 Model에서 forward해주는 것을 View에서 해줄 수 있기 때문에
        // ModelAndView라는 클래스를 만들어서 Model, View역할을 한 번에 해줌
        ModelAndView modelAndView = new ModelAndView("v3/m-list");
        modelAndView.addAttribute("memberList", memberList);

        return modelAndView;
    }
}
