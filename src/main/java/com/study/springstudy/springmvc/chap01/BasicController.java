package com.study.springstudy.springmvc.chap01;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

// 요청 처리하고싶을때
// controllerMap.put("/chap02/v4/detail", new DetailController()); 이런 행동 해줌
@Controller
@RequestMapping("/spring/chap01/*")
public class BasicController {
    // 디테일한 요청
    // URL : /spring/chap01/hello
    @RequestMapping("/hello")
    public String hello() {
        System.out.println("hello 요청");

        // /WEB-INF/views/hello.jsp
        return "hello"; // hello.jsp로 포워딩
    }

    // ========= 요청 파라미터 (Query String) 읽기 ========= //
    // URL에 붙어있거나 form태그에서 전송된 데이터

    // 1. HttpServletRequest 사용
    @RequestMapping("/person")
    public String person(HttpServletRequest request) {
        System.out.println("/person!!!");
        String name = request.getParameter("name");
        String age = request.getParameter("age");

        System.out.println("name = " + name);
        System.out.println("age = " + age);
        return "";
    }

    // 2. @RequestParam 사용하기
    // /spring/chap01/major?stu=kim&major=business&grade=3
    @RequestMapping("/major")
    public String major(
            // @RequestParam 생략가능
            // url에 major라고 쓰는것 나는 mj라고 부르겠다 -> 생략 불가능
            @RequestParam String stu,
            @RequestParam("major") String mj,
            int grade) {

        System.out.println("stu = " + stu);
        System.out.println("major = " + mj);
        System.out.println("grade = " + grade);

        return "";
    }

    // 3. 커맨드 객체 (RequestDTO) 사용해서 요청 파라미터 한 번에 읽어오기
    // ex) /spring/chap01/order?orderNum=22&goods=구두&amount=3&price=20000...
    @RequestMapping("/order")
    public String order(OrderDto order) {

        System.out.println("주문번호 = " + order.getOrderNum());
        System.out.println("주문상품명 = " + order.getGoods());
        System.out.println("주문개수 = " + order.getAmount());
        System.out.println("주문가격 = " + order.getPrice());

        return "";
    }
}
