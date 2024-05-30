package com.study.springstudy.springmvc.chap05.dto.request;

import com.study.springstudy.springmvc.chap05.entity.Member;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class SignUpDto {

    @NotBlank(message = "아이디는 필수값입니다.")
    @Size(min = 4, max =14, message = "아이디는 4~14글자")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "아이디는 영문과 숫자만 포함해야 합니다.")
    private String account;

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
