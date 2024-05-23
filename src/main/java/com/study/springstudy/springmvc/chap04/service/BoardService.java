package com.study.springstudy.springmvc.chap04.service;

import com.study.springstudy.springmvc.chap04.common.Page;
import com.study.springstudy.springmvc.chap04.common.Search;
import com.study.springstudy.springmvc.chap04.dto.BoarWriteRequestdDto;
import com.study.springstudy.springmvc.chap04.dto.BoardDetailResponseDto;
import com.study.springstudy.springmvc.chap04.dto.BoardListResponseDto;
import com.study.springstudy.springmvc.chap04.entity.Board;
import com.study.springstudy.springmvc.chap04.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardMapper mapper;

    // 목록 조회 중간처리
    public List<BoardListResponseDto> getList(Search page) {

        List<Board> BoardList = mapper.findAll(page);

        // 조회해온 게시물 리스트에서 각 게시물들의 조회수를 확인하여
        // 조회수가 5이상인 게시물에 특정 마킹
        List<BoardListResponseDto> dtoList = BoardList.stream()
                .map(b -> new BoardListResponseDto(b))
                .collect(Collectors.toList());
        return dtoList;
    }

    // 저장 중간처리
    public boolean insert(BoarWriteRequestdDto dto) {
        return mapper.save(new Board(dto));
    }

    // 삭제 중간처리
    public boolean remove(int boardNo) {
        return mapper.delete(boardNo);
    }

    // 개별조회 중간처리
    public BoardDetailResponseDto retrieve(int bno) {

        Board b = mapper.findOne(bno);
        // 조회수 상승
        if(b != null) mapper.upViewCount(bno);


        BoardDetailResponseDto dto = new BoardDetailResponseDto(b);

        return dto;
    }

    // 총 게시물수 중간 처리
    public int getCount(Search search) {
        return mapper.count(search);
    }
}
