package com.study.springstudy.springmvc.chap05.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
// 클라이언트 -> 서버 requestDto
public class ReplyPostDto {

    /*
    *  NotNull : null만 허용안됨
    *  NotEmpty : null은 되는데 빈문자는 안됨
    *  NotBlank : null도 안되고 빈문자도 안됨
    */

    @NotBlank
    @Size(min = 1, max = 300)
    private String text; // 댓글 내용
    @NotBlank
    @Size(min = 2, max = 8)
    private String author; // 댓글 작성자
    @NotNull
    private Long bno; // 원본 글번호
}