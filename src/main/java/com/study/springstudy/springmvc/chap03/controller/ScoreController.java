package com.study.springstudy.springmvc.chap03.controller;

import com.study.springstudy.springmvc.chap03.dto.ScoreDetailResponseDto;
import com.study.springstudy.springmvc.chap03.dto.ScoreListResponseDto;
import com.study.springstudy.springmvc.chap03.dto.ScoreModifyRequestDto;
import com.study.springstudy.springmvc.chap03.dto.ScorePostDto;
import com.study.springstudy.springmvc.chap03.entity.Score;
import com.study.springstudy.springmvc.chap03.repository.ScoreRepository;
import com.study.springstudy.springmvc.chap03.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

/*
    # 요청 URL
    1. 학생 성적정보 등록화면을 보여주고 및 성적정보 목록조회 처리
    - /score/list : GET

    2. 성적 정보 등록 처리 요청
    - /score/register : POST

    3. 성적정보 삭제 요청
    - /score/remove : GET

    4. 성적정보 상세 조회 요청
    - /score/detail : GET
 */
@Controller
@RequestMapping("/score")
@RequiredArgsConstructor
public class ScoreController {

    // 의존객체 설정
    private final ScoreService service;

//     @Autowired
//    public ScoreController(ScoreRepository repository) {
//        this.repository = repository;
//    }

    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "num") String sort, Model model) {

        List<ScoreListResponseDto> dtos = service.getList(sort);
        model.addAttribute("sList", dtos);

        return "score/score-list";
    }

    @PostMapping("/register")
    public String register(ScorePostDto dto) {

        // service를 거쳐서 데이터베이스에 저장
        service.insert(dto);

        // 다시 조회로 돌아가야 저장된 데이터를 볼 수 있음
        // 포워딩이 아닌 리다이렉트로 재요청을 넣어야 새롭게 디비를 조회
        return "redirect:/score/list";
    }

    @GetMapping("/remove")
    public String remove(long stuNum) {

        service.deleteScore(stuNum);
        return "redirect:/score/list";
    }

    @GetMapping("/detail")
    public String detail(long stuNum, Model model) {

        // 1. 상세조회를 원하는 학번을 읽기
        // 2. DB에 상세조회 요청
        // 3. DB에서 조회한 회원정보 JSP에게 전달
        // 4. rank 조회
        ScoreDetailResponseDto dto = service.retrieve(stuNum);
        model.addAttribute("s", dto);

        return "score/score-detail";
    }


    // 수정 화면 열기 요청
    // 수정화면을 열 때 detail에 있던 데이터를 들고와야함 (조회)
    // -> 수정되는 사람의 정보를 가져와야해서
    @GetMapping("/modify")
    public String modify(long stuNum, Model model) {

        ScoreDetailResponseDto dto = service.retrieve(stuNum);
        model.addAttribute("s", dto);
        return "score/score-modify";
    }

    // 수정 데이터 반영 요청
    @PostMapping("/modify")
    public String modify(ScoreModifyRequestDto dto) {

        // 1. 수정을 원하는 새로운 데이터 읽기 (국영수 점수  + 학번)
//        System.out.println("dto = " + dto);
        // 2. 서비스에게 수정 위임
        service.update(dto);

        return "redirect:/score/detail?stuNum=" + dto.getStuNum();
    }
}