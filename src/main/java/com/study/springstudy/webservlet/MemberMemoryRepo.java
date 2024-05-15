package com.study.springstudy.webservlet;

import com.study.springstudy.webservlet.entity.Member;

import java.util.ArrayList;
import java.util.List;

public class MemberMemoryRepo {

    // 싱글톤 패턴으로 저장소 1개 생성
    private MemberMemoryRepo() {
    }

    private static MemberMemoryRepo repo = new MemberMemoryRepo();

    public static MemberMemoryRepo getInstance() {
        return repo;
    }

    // 필드
    private List<Member> memberList = new ArrayList<>();

    // 멤버 저장 기능
    public void save(Member member) {
        memberList.add(member);
//        System.out.println(memberList);
    }

    // 멤버 전체 조회
    public List<Member> findAll() {
        return memberList;
    }
}
