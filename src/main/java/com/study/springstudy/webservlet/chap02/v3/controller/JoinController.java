package com.study.springstudy.webservlet.chap02.v3.controller;

import com.study.springstudy.webservlet.ModelAndView;
import com.study.springstudy.webservlet.View;

import java.util.Map;

public class JoinController implements ControllerV3 {
    @Override
    public ModelAndView process(Map<String, String> paramMap) {
        // 파라미터를 받을 필요가 없음
        return new ModelAndView("v3/reg_form");
    }
}
