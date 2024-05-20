package com.study.springstudy.springmvc.chap04.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// dto의 필드명은 반드시 html form태그의 입력태그들의 name과 일치해야함
// 전달해주어야 하는 객체
@Setter @Getter @ToString
public class BoardDto {

    private String title;
    private String content;
    private String writer;

}
