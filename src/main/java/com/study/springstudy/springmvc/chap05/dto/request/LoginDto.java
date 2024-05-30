package com.study.springstudy.springmvc.chap05.dto.request;

import lombok.*;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDto {

    private String account;
    private String password;
    private boolean autoLogin; // 자동로그인 체크 여부
}
