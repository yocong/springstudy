package com.study.springstudy.springmvc.chap05.dto.response;

import lombok.*;

import java.time.LocalDateTime;

// 회원과 댓글 테이블의 조인한 결과 테이블을 포장하는 객체
// -> 프로필 이미지를 가져온 것을 포장하는 객체
@Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyFindAllDto {

    private long replyNo;
    private String replyText;
    private String replyWriter;
    private LocalDateTime replyDate;
    private long boardNo;
    private String account;
    private String profileImg;
}
