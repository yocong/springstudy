package com.study.springstudy.springmvc.chap03.mapper;

import com.study.springstudy.springmvc.chap03.dto.RankDto;
import com.study.springstudy.springmvc.chap03.dto.ScoreDetailResponseDto;
import com.study.springstudy.springmvc.chap03.entity.Score;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScoreMapper {

    // 저장소에 데이터 추가하기
    boolean save(Score score);

    // 저장소에서 데이터 전체조회하기
    List<Score> findAll(String sort);

    // 저장소에서 데이터 개별조회하기
    Score findOne(long stuNum);

    // 저장소에서 데이터 삭제하기
    boolean delete(long stuNum);

    // 저장소에서 등수, 전체인원 조회하기
    RankDto findRankByStuNum(long stuNum);

    // 저장소에서 국영수 점수 수정하기
    boolean updateScore(Score s);


}
