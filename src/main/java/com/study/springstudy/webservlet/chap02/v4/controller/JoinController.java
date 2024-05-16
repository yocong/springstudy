package com.study.springstudy.webservlet.chap02.v4.controller;

import com.study.springstudy.webservlet.Model;

import java.util.Map;

public class JoinController implements ControllerV4{
    @Override
    public String process(Map<String, String> paramMap, Model model) {
        return "v4/reg_form"; // 경로문자열만 보내면 알아서 나머지 과정 처리
    }
}
