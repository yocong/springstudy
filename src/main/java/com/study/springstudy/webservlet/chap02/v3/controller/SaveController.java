package com.study.springstudy.webservlet.chap02.v3.controller;


import com.study.springstudy.webservlet.MemberMemoryRepo;
import com.study.springstudy.webservlet.ModelAndView;
import com.study.springstudy.webservlet.View;
import com.study.springstudy.webservlet.entity.Member;

import java.util.Map;

public class SaveController implements ControllerV3 {

    private MemberMemoryRepo repo = MemberMemoryRepo.getInstance();

    @Override
    public ModelAndView process(Map<String, String> paramMap) {

        // 외부 paramMap에 담겨있는 값을 꺼내서 사용
        String userName = paramMap.get("userName");
        String account = paramMap.get("account");
        String password = paramMap.get("password");

        Member member = new Member(account, password, userName);
        repo.save(member);

        return new ModelAndView("redirect:/chap02/v3/show");
    }
}
