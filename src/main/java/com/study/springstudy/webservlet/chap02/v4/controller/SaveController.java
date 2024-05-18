package com.study.springstudy.webservlet.chap02.v4.controller;

import com.study.springstudy.webservlet.MemberMemoryRepo;
import com.study.springstudy.webservlet.MyModel;
import com.study.springstudy.webservlet.entity.Member;

import java.util.Map;

public class SaveController implements ControllerV4 {

    private MemberMemoryRepo repo = MemberMemoryRepo.getInstance();

    @Override
    public String process(Map<String, String> paramMap, MyModel model) {
        String userName = paramMap.get("userName");
        String account = paramMap.get("account");
        String password = paramMap.get("password");

        Member member = new Member(account, password, userName);

        repo.save(member);

        return "redirect:/chap02/v4/show"; // 리다이렉트, ModelAndView처리 알아서 해줘
    }
}
