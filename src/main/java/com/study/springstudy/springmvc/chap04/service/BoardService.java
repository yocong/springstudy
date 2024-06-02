package com.study.springstudy.springmvc.chap04.service;

import com.study.springstudy.springmvc.chap04.common.Search;
import com.study.springstudy.springmvc.chap04.dto.BoarWriteRequestdDto;
import com.study.springstudy.springmvc.chap04.dto.BoardDetailResponseDto;
import com.study.springstudy.springmvc.chap04.dto.BoardFindAllDto;
import com.study.springstudy.springmvc.chap04.dto.BoardListResponseDto;
import com.study.springstudy.springmvc.chap04.entity.Board;
import com.study.springstudy.springmvc.chap04.mapper.BoardMapper;
import com.study.springstudy.springmvc.chap05.mapper.ReplyMapper;
import com.study.springstudy.springmvc.chap05.entity.Reply;
import com.study.springstudy.springmvc.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardMapper boardMapper;
    private final ReplyMapper replyMapper;

    // 목록 조회 중간처리
    public List<BoardListResponseDto> getList(Search page) {

        List<BoardFindAllDto> BoardList = boardMapper.findAll(page);

        // 조회해온 게시물 리스트에서 각 게시물들의 조회수를 확인하여
        // 조회수가 5이상인 게시물에 특정 마킹
        List<BoardListResponseDto> dtoList = BoardList.stream()
                .map(b -> new BoardListResponseDto(b))
                .collect(Collectors.toList());
        return dtoList;
    }

    // 저장 중간처리
    public boolean insert(BoarWriteRequestdDto dto, HttpSession session) {
        // db에 접근하기 위해 dto -> entity로 바꿔서 접근
        Board b = dto.toEntity();
        // 계정명을 엔터티에 추가 - 세션에서 계정명 가져오기
        b.setAccount(LoginUtil.getLoggedInUserAccount(session));

        return boardMapper.save(b);
    }

    // 삭제 중간처리
    public boolean remove(int boardNo) {
        return boardMapper.delete(boardNo);
    }

    // 상세 조회 요청 중간처리
    public BoardDetailResponseDto detail(int bno) {
        Board b = boardMapper.findOne(bno);
        // 조회수 상승
        if(b != null) boardMapper.upViewCount(bno);

        // 상세조회 할 때 댓글 목록을 같이 조회
//        List<Reply> replies = replyMapper.findAll(bno);

        BoardDetailResponseDto responseDto = new BoardDetailResponseDto(b);
        // 댓글 목록을 Dto로 보내
//        responseDto.setReplies(replies);
        return responseDto;
    }

    // 총 게시물수 중간 처리
    public int getCount(Search search) {
        return boardMapper.count(search);
    }
}
