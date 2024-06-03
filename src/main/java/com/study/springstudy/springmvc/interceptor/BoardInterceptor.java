package com.study.springstudy.springmvc.interceptor;

import com.study.springstudy.springmvc.chap04.entity.Board;
import com.study.springstudy.springmvc.chap04.mapper.BoardMapper;
import com.study.springstudy.springmvc.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.study.springstudy.springmvc.util.LoginUtil.*;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class BoardInterceptor implements HandlerInterceptor {

    private final BoardMapper boardMapper;

    // preHandle을 구현하여
    // 로그인을 안한 회원은 글쓰기, 글수정, 글삭제 요청을 거부할 것!
    // 거부하고 로그인 페이지로 리다이렉션할 것!
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();

        // 요청 URL
        String redirectUri = request.getRequestURI();

        if (!isLoggedIn(session)) {
            log.info("origin: {}", redirectUri);
            response.sendRedirect("/members/sign-in?message=login-required&redirect=" + redirectUri);
            return false;
        }

        // 삭제요청이 들어오면 서버에서 한번더 관리자인지? 자기가쓴글인지 체크
        // 관리자인가?
        if (isAdmin(session)) {
            return true;
        }

        // 삭제요청인지?
        if (redirectUri.equals("/board/delete")) {
            // 내가 쓴 글이 아닌지??
            // 현재 삭제하려는 글의 글쓴이 계정명과
            // -> DB에서 조회해보면 됨 -> 글번호가 필요함
            int bno = Integer.parseInt(request.getParameter("bno"));
            Board board = boardMapper.findOne(bno);
            String boardAccount = board.getAccount();

            // 현재 로그인한 회원의 계정명을 구해서
            String loggedInUserAccount = getLoggedInUserAccount(session);

            // 대조해보는 작업이 필요함
            if (!   isMine(boardAccount, loggedInUserAccount)) {
                response.sendRedirect("/access-deny?message=authorization");
                return false;
            }

        }
        return true;
    }
}
