package com.study.springstudy.chap03;

/*
 * @Solution
 * - 객체 생성의 제어권을 이 클래스에서
 *   다른 클래스로 이전한다.
 *   ex) new 생성자(); - 이 문법을 담당클래스를 정해서 몰아서 수행시킴
 * - 호텔 객체 생성시 반드시 객체를 전달하도록 강요 (생성자를 통해서)
 *
 * // 제어의 역전(IoC) : 객체 생성의 제어권을 외부로 넘긴다.
    // 의존성 주입(DI) : 외부에서 생성된 객체를 주입받는 개념
 */

public class Hotel {

    // 레스토랑
    private Restaurant restaurant;

    // 헤드쉐프
    private Chef headChef;

    // restaurant, headChef 있어야 Hotel 지을 수 있어~
    public Hotel(Restaurant restaurant, Chef headChef) {
        this.restaurant = restaurant;
        this.headChef = headChef;
    }

    // 호텔을 소개하는 기능
    public void inform() {
        System.out.printf("우리 호텔의 레스토랑은 %s입니다. " +
                "그리고 헤드쉐프는 %s입니다.\n"
                , restaurant.getClass().getSimpleName()
                , headChef.getClass().getSimpleName());

        restaurant.order();
    }
}
