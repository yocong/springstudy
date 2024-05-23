package com.study.springstudy.springmvc.chap04.dto;

import com.study.springstudy.springmvc.chap04.entity.Board;
import com.study.springstudy.springmvc.chap05.entity.Reply;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
public class BoardDetailResponseDto {

    private int boardNo;
    private String writer;
    private String title;
    private String content;
    private String regDateTime;

    @Setter
    private List<Reply> replies;

    public BoardDetailResponseDto(Board b) {
        this.boardNo = b.getBoardNo();
        this.title = b.getTitle();
        this.writer = b.getWriter();
        this.content = b.getContent();

        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 a hh시 mm분 ss초");
        this.regDateTime = pattern.format(b.getRegDateTime());
    }
}
