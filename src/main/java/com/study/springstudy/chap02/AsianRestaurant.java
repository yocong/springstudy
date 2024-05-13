package com.study.springstudy.chap02;

public class AsianRestaurant implements Restaurant{

    private Chef chef = new KimuraChef();

    private Course course = new SushiCourse();

    public void order() {
        System.out.println("아시안 요리를 주문합니다.");
        course.combineMenu();
        chef.cook();
    }
}
