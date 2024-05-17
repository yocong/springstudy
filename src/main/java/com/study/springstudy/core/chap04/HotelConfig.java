package com.study.springstudy.core.chap04;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// 스프링 설정파일
@Configuration
// 패키지 내부에 있는 @Component붙은 객체들을 관리하겠다.
@ComponentScan(basePackages = "com.study.springstudy.core")
public class HotelConfig {
}
