package com.study.springstudy.chap02;

public class WesternRestaurant implements Restaurant {

    // 담당 쉐프
    private Chef chef = new JannChef();

    // 요리 코스
    private Course course = new FrenchCourse();

    // 주문 기능
    public void order() {
        System.out.println("서양 요리를 주문합니다.");
        course.combineMenu();
        chef.cook();
    }
}
