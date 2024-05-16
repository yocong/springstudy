package com.study.springstudy.springmvc.chap02;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// 요청 처리
@Controller
@RequestMapping("/coffee/*") // URL 매핑
public class CoffeeController {

    /*
    *  @request-uri : /coffee/order
    *  @forwarding-jsp : /WEB-INF/views/mvc/coffee-form.jsp
    */

    // GET 요청만 받겠다
//    @RequestMapping(value = "/order", method = RequestMethod.GET)
    @GetMapping("/order")
    public String order() {
        return "mvc/coffee-form";
    }

    // POST 요청만 받겠다
//    @RequestMapping(value = "/result", method = RequestMethod.POST)
    @PostMapping("/result") // 위와 동일
    public String result(String menu, int price, Model model) { // @RequestParam 생략 가능

        // 1. 주문 데이터 (menu, price) 읽어오기

        // 2. jsp에 보내서 렌더링
        model.addAttribute("mmm", menu);
        model.addAttribute("ppp", price);

        return "mvc/coffee-result";
    }
}
