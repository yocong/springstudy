package com.study.springstudy.springmvc.chap03.repository;

import com.study.springstudy.springmvc.chap03.entity.Score;

import java.util.List;

// 역할: 적당한 저장소에 CRUD하기
public interface ScoreRepository {

    // 저장소에 데이터 추가하기
    boolean save(Score score);

    // 저장소에서 데이터 전체조회하기
    // 리스트로 Score들을 전부 갖다줘
    List<Score> findAll(String sort);

    // 저장소에서 데이터 개별조회하기
    Score findOne(long stuNum);

    // 저장소에서 데이터 삭제하기
    // -> 원래는 이 인터페이스를 구현하는 구현체에서 모두 오버라이딩 해야되는데
    //    default로 인해 필요한 곳에서만 오버라이딩해서 사용가능
    default boolean delete(long stuNum) {
        return false;
    }
}
