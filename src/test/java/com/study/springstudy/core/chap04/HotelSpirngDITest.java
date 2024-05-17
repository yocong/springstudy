package com.study.springstudy.core.chap04;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

class HotelSpirngDITest {


    @Test
    void diTest() {
        AnnotationConfigApplicationContext context
                = new AnnotationConfigApplicationContext(HotelConfig.class);

            // @Component함으로써 new() 처럼 객체 생성을 하지않아도된다.
        Hotel hotel = context.getBean(Hotel.class);
        hotel.inform();

    }

}