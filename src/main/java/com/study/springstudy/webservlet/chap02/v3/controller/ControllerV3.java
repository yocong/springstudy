package com.study.springstudy.webservlet.chap02.v3.controller;

import com.study.springstudy.webservlet.ModelAndView;
import com.study.springstudy.webservlet.View;

import java.util.Map;

/*
*   이 인터페이스를 구현하는 다양한 하위 객체들이
*   요청정보(req)나 응답정보(resp)를 모두가 사용하는 것이 아니기 때문에
*   요청, 응답 정보 처리를 외부로 위임
*/
public interface ControllerV3 {

    /*
    * 요청이 들어오면 적절한 처리를 수행
    * @param paramMap: 요청 정보 (쿼리파라미터) 를 모두 읽어서 맵에 담음
    * @return 응답시 보여줄 화면 처리객체(View)와
    *         화면처리를 위해 사용할 데이터(Model)을
    *         일괄적으로 처리하는 객체인 ModelAndView
    */
    ModelAndView process(Map<String, String> paramMap);
}
