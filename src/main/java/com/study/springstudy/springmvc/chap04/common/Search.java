package com.study.springstudy.springmvc.chap04.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@EqualsAndHashCode
// 검색 + 페이징
public class Search extends Page {

    // 검색어, 검색조건
    private String keyword, type;

    // 검색 기본값은 빈문자 = 전체 출력
    public Search() {
        this.keyword = "";
    }
}
