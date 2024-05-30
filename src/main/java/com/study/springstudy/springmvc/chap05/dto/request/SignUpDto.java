package com.study.springstudy.springmvc.chap05.dto.request;

import com.study.springstudy.springmvc.chap05.entity.Member;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class SignUpDto {

    @NotBlank
    @Size(min = 4, max =14) private String account;

    @NotBlank
    private String password;

    @NotBlank
    @Size(min = 2, max = 6) private String name;

    @NotBlank
    private String email;

    // dto -> 엔터티로 변환
    public Member toEntity() {

        return Member.builder()
                .account(this.account)
                .password(this.password)
                .email(this.email)
                .name(this.name)
                .build();
    }
}
