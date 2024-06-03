package com.study.springstudy.springmvc.chap05.mapper;

import com.study.springstudy.springmvc.chap05.entity.React;
import com.study.springstudy.springmvc.chap05.entity.Reaction;
import com.study.springstudy.springmvc.chap05.entity.ViewLog;
import org.apache.ibatis.annotations.Param;

public interface ReactionMapper {

    // 좋아요 기록 추가
    void insertReaction(Reaction reaction);

    // 좋아요 기록 삭제
    void deleteReaction(@Param("account") String account, @Param("bno") int bno);

    // 좋아요 기록 조회
    Reaction findReaction(@Param("account") String account, @Param("bno") long bno);

    // 특정 게시글의 좋아요, 싫어요 개수 조회
    int countReactions(@Param("boardNo") int boardNo, @Param("reactionType") React reactionType);
}
