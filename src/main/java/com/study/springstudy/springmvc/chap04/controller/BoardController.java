package com.study.springstudy.springmvc.chap04.controller;

import com.study.springstudy.springmvc.chap04.common.PageMaker;
import com.study.springstudy.springmvc.chap04.common.Search;
import com.study.springstudy.springmvc.chap04.dto.BoardWriteRequestDto;
import com.study.springstudy.springmvc.chap04.dto.BoardDetailResponseDto;
import com.study.springstudy.springmvc.chap04.dto.BoardListResponseDto;
import com.study.springstudy.springmvc.chap04.service.BoardService;
import com.study.springstudy.springmvc.chap05.dto.response.ReactionDto;
import com.study.springstudy.springmvc.chap05.service.ReactionService;
import com.study.springstudy.springmvc.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    // 의존객체
    private final BoardService boardService;
    private final ReactionService reactionService;

    // 1. 목록 조회 요청 (/board/list : GET)
    @GetMapping("/list")
    // 타입을 Search로 받게되면 Page와 Search에 있는 것들을 모두 받을 수 있음
    // -> findAll의 keyword까지 O
    public String list(@ModelAttribute("s") Search page, Model model) {

        // 서비스에게 조회요청 위임
        List<BoardListResponseDto> dtos = boardService.findList(page);
        // 페이지 정보를 생성하여 JSP에게 전송
        PageMaker maker = new PageMaker(page, boardService.getCount(page));

        model.addAttribute("bList", dtos);
        model.addAttribute("maker", maker);
        return "board/list";
    }

    // 2. 글쓰기 양식 화면 열기 요청 (/board/write : GET)
    @GetMapping("/write")
    public String open() {

        return "board/write";
    }

    // 3. 게시글 등록 요청 (/board/write : POST)
    // -> 목록 조회 요청 리다이렉션
    @PostMapping("/write")
    public String register(BoardWriteRequestDto dto, HttpSession session) {

        boardService.insert(dto, session);

        return "redirect:/board/list";
    }

    // 4. 게시글 삭제 요청 (/board/delete : GET)
    // -> 목록 조회 요청 리다이렉션
    @GetMapping("/delete")
    public String delete(@RequestParam int bno) {

        boardService.remove(bno);

        return "redirect:/board/list";
    }

    // 5. 게시글 상세 조회 요청 (/board/detail : GET)
    @GetMapping("/detail")
    public String detail(int bno, Model model, HttpServletRequest request, HttpServletResponse response) {

        // 2. 데이터베이스로부터 해당 글번호 데이터 조회하기
        BoardDetailResponseDto b = boardService.detail(bno, request, response);

        // 3. JSP파일에 조회한 데이터 보내기
        model.addAttribute("bbb", b);

        // 4. 요청 헤더를 파싱하여 이전 페이지의 주소를 얻어냄
        String ref = request.getHeader("Referer");
        model.addAttribute("ref", ref);


        return "board/detail";
    }

    // 좋아요 요청 비동기 처리
    @GetMapping("/like")
    @ResponseBody
    public ResponseEntity<?> like(long bno, HttpSession session) {

        // 로그인 안했는데 좋아요 눌렀어? 로그인하고와
        if (!LoginUtil.isLoggedIn(session)) {
            return ResponseEntity.status(403)
                    .body("로그인이 필요합니다.");
        }

        log.info("like async request");

        String account = LoginUtil.getLoggedInUserAccount(session);
        ReactionDto dto = reactionService.like(bno, account);// 좋아요 요청 처리

        return ResponseEntity.ok().body(dto);
    }

    // 싫어요 요청 비동기 처리
    @GetMapping("/dislike")
    @ResponseBody
    public ResponseEntity<?> dislike(long bno, HttpSession session) {

        // 로그인 안했는데 싫어요 눌렀어? 로그인하고와
        if (!LoginUtil.isLoggedIn(session)) {
            return ResponseEntity.status(403)
                    .body("로그인이 필요합니다.");
        }

        log.info("dislike async request");

        String account = LoginUtil.getLoggedInUserAccount(session);

        ReactionDto dto = reactionService.dislike(bno, account);// 싫어요 요청 처리
        return ResponseEntity.ok().body(dto);
    }
}
