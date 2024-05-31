package com.study.springstudy.springmvc.chap05.dto.response;

import com.study.springstudy.springmvc.chap05.entity.Member;
import lombok.*;

@Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUserInfoDto {

    // 클라이언트에 보낼 정보
    private String account;
    private String nickname;
    private String email;
    private String auth;

    public LoginUserInfoDto(Member member) {
        this.account = member.getAccount();
        this.email = member.getEmail();
        this.nickname = member.getName();
        this.auth = member.getAuth().name();
    }
}
