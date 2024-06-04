package com.study.springstudy.springmvc.util;

import com.study.springstudy.springmvc.chap05.dto.response.LoginUserInfoDto;
import com.study.springstudy.springmvc.chap05.entity.Auth;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

// 로그인과 관련된 여러 가지 유용한 기능을 제공
public class LoginUtil {

    public static final String LOGIN = "login";
    public static final String AUTO_LOGIN_COOKIE = "auto";

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

    // 로그인한 회원정보 얻기
    public static LoginUserInfoDto getLoggedInUser(HttpSession session) {
        return (LoginUserInfoDto) session.getAttribute(LOGIN);
    }

    // 로그인한 회원의 권한정보가 관리자면 반환
    public static boolean isAdmin(HttpSession session) {
        LoginUserInfoDto loggedInUser = getLoggedInUser(session);
        Auth auth = null;
        if (isLoggedIn(session)) {
            auth = Auth.valueOf(loggedInUser.getAuth());
        }
        return auth == Auth.ADMIN;
    }

    // 본인 글인가?, boardAccount, loggedInUserAccount가 같으면 true
    public static boolean isMine(String boardAccount, String loggedInUserAccount) {
        return boardAccount.equals(loggedInUserAccount);
    }

    // 쿠키가 있다면? 자동로그인 상태!
    public static boolean isAutoLogin(HttpServletRequest request) {
        Cookie autoLoginCookie = WebUtils.getCookie(request, AUTO_LOGIN_COOKIE);
        return autoLoginCookie!= null;
    }
}
