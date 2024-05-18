package com.study.springstudy.springmvc.chap04.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 전달해주어야 하는 객체
@Setter @Getter @ToString
public class BoardDto {

    private String title;
    private String content;
    private String writer;

}
