package com.study.springstudy.springmvc.chap05.service;
import com.study.springstudy.springmvc.chap04.common.Page;
import com.study.springstudy.springmvc.chap04.common.PageMaker;
import com.study.springstudy.springmvc.chap05.dto.request.ReplyModifyDto;
import com.study.springstudy.springmvc.chap05.dto.request.ReplyPostDto;
import com.study.springstudy.springmvc.chap05.dto.response.ReplyDetailDto;
import com.study.springstudy.springmvc.chap05.dto.response.ReplyFindAllDto;
import com.study.springstudy.springmvc.chap05.dto.response.ReplyListDto;
import com.study.springstudy.springmvc.chap05.entity.Reply;
import com.study.springstudy.springmvc.chap05.mapper.ReplyMapper;
import com.study.springstudy.springmvc.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j // 로그 라이브러리
public class ReplyService {

    private final ReplyMapper replyMapper;

    // 댓글 목록 전체조회
    public ReplyListDto getReplies(long boardNo, Page page) {
        List<ReplyFindAllDto> replies = replyMapper.findAll(boardNo, page);
        List<ReplyDetailDto> dtoList = replies.stream()
                .map(r -> new ReplyDetailDto(r))
                .collect(Collectors.toList());

        return ReplyListDto.builder()
                .replies(dtoList)  // 댓글 목록
                .pageInfo(new PageMaker(page, replyMapper.count(boardNo)))  // 페이지 정보
                .build();  // ReplyListDto 객체 생성 및 반환
    }

    // 댓글 입력
    public boolean register(ReplyPostDto dto, HttpSession session) {
        Reply reply = Reply.builder()
                .replyText(dto.getText())
                .replyWriter(dto.getAuthor())
                .boardNo(dto.getBno())
                .account(LoginUtil.getLoggedInUserAccount(session))
                .build();

        boolean flag = replyMapper.save(reply);
        if (flag) log.info("댓글 등록 성공 - {}", dto);
        else log.warn("댓글 등록 실패");
        return flag;
    }

    // 댓글 수정
    public ReplyListDto modify(ReplyModifyDto dto) {
        // dto -> entity로 변환
        replyMapper.modify(dto.toEntity());
        // 수정하고 다시 댓글 목록 반환 (수정사항 반영)
        return getReplies(dto.getBno(), new Page(1, 10));
    }

    // 댓글 삭제
    @Transactional
    // 삭제시 에러나 실패했을 때 원래의 데이터를 유지하게 하기위해서 @Transactional 어노테이션을 붙임
    public ReplyListDto remove(long rno) {
        // 댓글 번호로 원본 글번호 찾기
        long bno = replyMapper.findBno(rno);
        boolean flag = replyMapper.delete(rno);
        // 삭제 후 삭제된 목록을 리턴
        return flag ? getReplies(bno, new Page(1, 10)) : null;
    }
}
