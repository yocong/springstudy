package com.study.springstudy.chap01;

import com.study.springstudy.core.chap01.Hotel;
import org.junit.jupiter.api.Test;

class HotelTest {

    @Test
    void hotelTest() {

        Hotel hotel = new Hotel();
        hotel.inform();
    }
}