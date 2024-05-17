package com.study.springstudy.springmvc;

import lombok.*;

@Setter @Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드 초기화 생성자
//@RequiredArgsConstructor // 필수 파라미터(final 필드만) 초기화 생성자
public class Student {

//    private final String name;
    private int age;
    private int grade;
}
