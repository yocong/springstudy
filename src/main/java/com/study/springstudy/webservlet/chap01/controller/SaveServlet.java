package com.study.springstudy.webservlet.chap01.controller;

import com.study.springstudy.webservlet.MemberMemoryRepo;
import com.study.springstudy.webservlet.entity.Member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/chap01/save")
public class SaveServlet extends HttpServlet {

    private MemberMemoryRepo repo = MemberMemoryRepo.getInstance();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 1. 회원가입 폼에서 넘어온 데이터 읽기
        String userName = req.getParameter("userName");
        String account = req.getParameter("account");
        String password = req.getParameter("password");

        // 2. 회원 정보를 객체로 포장하여 적절한 저장소에 저장
        Member member = new Member(account, password, userName);
        repo.save(member);

        // 3. 적절한 페이지로 이동 - 조회화면으로 리다이렉트
//       - 일반적으로 데이터를 변경하는 작업인 경우(예: 저장, 수정, 삭제 등), 보통 redirect를 사용
//       -> 데이터를 변경한 후에 리다이렉트를 사용하면, 사용자가 새로고침을 하더라도 중복 데이터 변경을 방지
//       -> 또한 새로고침을 눌렀을 때 경고메시지나 에러 방지
        resp.sendRedirect("/chap01/show");

    }
}
