package com.study.springstudy.springmvc.chap03.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 수정해야하는 부분을 포장하는 객체
@Getter @Setter @ToString
public class ScoreModifyRequestDto {

    private long stuNum;
    private int kor, eng, math;
}
