package com.study.springstudy.springmvc.chap04.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// dto의 필드명은 반드시 html form태그의 입력태그들의 name과 일치해야함
// 요청 처리에 필요한 데이터 포장하는 객체
@Setter @Getter @ToString
public class BoarWriteRequestdDto {

    private String title;
    private String content;
    private String writer;

}
