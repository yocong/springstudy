//package com.study.springstudy.springmvc.chap03.controller;
//
//import com.study.springstudy.springmvc.chap03.dto.ScorePostDto;
//import com.study.springstudy.springmvc.chap03.entity.Score;
//import com.study.springstudy.springmvc.chap03.repository.ScoreRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.Comparator;
//import java.util.List;
//import java.util.stream.Collectors;
//
///*
//    # 요청 URL
//    1. 학생 성적정보 등록화면을 보여주고 및 성적정보 목록조회 처리
//    - /score/list : GET
//
//    2. 성적 정보 등록 처리 요청
//    - /score/register : POST
//
//    3. 성적정보 삭제 요청
//    - /score/remove : GET
//
//    4. 성적정보 상세 조회 요청
//    - /score/detail : GET
// */
//
//@Controller
//@RequestMapping("/score") // /score URL에 대한 요청 처리
//public class ScoreController {
//
//    // 의존객체 설정
//    // DIP를 지키기 위해서 추상화된 인터페이스 만들어서 구체화
//    private final ScoreRepository repository;
//
//    // 생성자를 통해 자동으로 객체를 주입해줘 (의존성 주입)
//    @Autowired
//    public ScoreController(ScoreRepository repository) {
//        this.repository = repository;
//    }
//
//    // @RequestParam(defaultValue = "num") : 기본 정렬을 num으로
//    @GetMapping("/list")
//    public String list(@RequestParam(defaultValue = "num") String sort, Model model) {
//
//        List<Score> scoreList = repository.findAll(sort);
//
//        model.addAttribute("sList", scoreList);
//
//        return "score/score-list";
//    }
//
//    @PostMapping("/register")
//    public String register(ScorePostDto dto) {
//
//        // 데이터베이스에 저장
//        // 성적정보에 대한 것은 dto로 줄테니까 나머지(평균, 합산, 등급)는 Score에서 만들어
//        Score score = new Score(dto);
//        repository.save(score);
//
//        // 다시 조회로 돌아가야 저장된 데이터를 볼 수 있음
//        // 포워딩이 아닌 리다이렉트로 재요청을 넣어야 새롭게 디비를 조회
//        return "redirect:/score/list";
//    }
//
//    @GetMapping("/remove")
//    public String remove(long stuNum) {
//        // 1. 삭제를 원하는 학번을 읽기
//
//        // 2. 저장소에 연결하여 삭제처리를 진행시킨다.
//        repository.delete(stuNum);
//
//        // 3. 삭제가 완료된 화면을 띄우기 위해 조회요청으로 리다이렉션한다.
//        return "redirect:/score/list";
//    }
//
//    @GetMapping("/detail")
//    public String detail(long stuNum, Model model) {
//        // 1. 상세조회를 원하는 학번을 읽기
//        // 2. DB에 상세조회 요청
//        Score score = repository.findOne(stuNum);
//        // 3. DB에서 조회한 회원정보 JSP에게 전달
//        model.addAttribute("s", score);
//
//
//        return "score/score-detail";
//    }
//
//}
