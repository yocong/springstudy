package com.study.springstudy.springmvc.util;

import com.study.springstudy.springmvc.chap05.dto.response.LoginUserInfoDto;

import javax.servlet.http.HttpSession;

// 로그인과 관련된 여러 가지 유용한 기능을 제공
public class LoginUtil {

    public static final String LOGIN = "login";

    // 로그인 여부 확인
    public static boolean isLoggedIn(HttpSession session) {
        return session.getAttribute(LOGIN) != null;
    }

    // 로그인한 회원의 계정명 얻기
    public static String getLoggedInUserAccount(HttpSession session) {
        LoginUserInfoDto currentUser
                = (LoginUserInfoDto) session.getAttribute(LOGIN);
        return  (currentUser != null) ? currentUser.getAccount() : null;
    }
}
