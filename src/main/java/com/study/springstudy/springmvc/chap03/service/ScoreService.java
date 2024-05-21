package com.study.springstudy.springmvc.chap03.service;

/*
    컨트롤러와 레파지토리사이에 위치하여
    중간 처리를 담당

    - 트랜잭션 처리, 데이터 가공 처리...

    - 의존 관계
    Controller -> Service -> Repository
 */

import com.study.springstudy.springmvc.chap03.dto.*;
import com.study.springstudy.springmvc.chap03.entity.Score;
import com.study.springstudy.springmvc.chap03.mapper.ScoreMapper;
import com.study.springstudy.springmvc.chap03.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ScoreService {

//    private final ScoreRepository repository;
    private final ScoreMapper repository;

    // 목록 조회 중간처리
    // - DB에서 조회한 성적정보 목록은 민감한정보를 모두 포함하고 있는데
    //   이를 컨트롤러에게 직접 넘기면 보안상 불필요한 정보까지 화면으로
    //   넘어갈 수 있기 때문에 숨길건 숨기고 뺄건 빼는 데이터가공을 처리한다.
    public List<ScoreListResponseDto> getList(String sort) {
        List<Score> scoreList = repository.findAll(sort);
        return scoreList.stream()
                .map(s -> new ScoreListResponseDto(s))
                .collect(Collectors.toList());
    }

    // 저장 중간처리
    public boolean insert(ScorePostDto dto) {
        return repository.save(new Score(dto));
    }

    // 삭제 중간처리
    public boolean deleteScore(long stuNum) {
        return repository.delete(stuNum);
    }

    // 개별조회 중간처리 (rank, count 반영)
    public ScoreDetailResponseDto retrieve(long stuNum) {

        Score score = repository.findOne(stuNum);
        RankDto result = repository.findRankByStuNum(stuNum);

        ScoreDetailResponseDto dto = new ScoreDetailResponseDto(score, result.getRank(), result.getCnt());

        return dto;
    }

    // 수정 완료를 위해 Service 클래스에서 dto를 entity로 변환
    public void update(ScoreModifyRequestDto dto) {

        repository.updateScore(new Score(dto));
    }
}
