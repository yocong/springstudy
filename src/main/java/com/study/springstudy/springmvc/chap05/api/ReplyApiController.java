package com.study.springstudy.springmvc.chap05.api;

import com.study.springstudy.springmvc.chap04.common.Page;
import com.study.springstudy.springmvc.chap05.dto.request.ReplyModifyDto;
import com.study.springstudy.springmvc.chap05.dto.request.ReplyPostDto;
import com.study.springstudy.springmvc.chap05.dto.response.ReplyDetailDto;
import com.study.springstudy.springmvc.chap05.dto.response.ReplyListDto;
import com.study.springstudy.springmvc.chap05.entity.Reply;
import com.study.springstudy.springmvc.chap05.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController // Controller + 반환시 JSON형식으로 변환
@RequestMapping("/api/v1/replies")
@RequiredArgsConstructor
@Slf4j // 로그 라이브러리
@CrossOrigin // CORS 정책 허용범위 설정, 나머지는 다 차단
public class ReplyApiController {

    private final ReplyService replyService;

    // 댓글 목록 조회 요청
    // URL : /api/v1/replies/원본글번호   -  GET -> 목록조회
    // @PathVariable : URL에 붙어있는 변수값을 읽는 어노테이션
    @GetMapping("/{bno}/page/{pageNo}")
    public ResponseEntity<?> list(
            @PathVariable long bno
            , @PathVariable int pageNo
    ) {

        if (bno == 0) {
            String message = "글 번호는 0번이 될 수 없습니다.";
            log.warn(message);
            return ResponseEntity
                    .badRequest()
                    .body(message);
        }

        log.info("/api/v1/replies/{} : GET", bno);

        ReplyListDto replies = replyService.getReplies(bno, new Page(pageNo, 10));

        return ResponseEntity
                .ok()
                .body(replies);
    }

    // 댓글 생성 요청 (요청할 때 JSON으로!!)
    // @RequestBody : 클라이언트가 전송한 데이터를 JSON으로 받아서 파싱
    // = JSON 데이터 -> 자바 객체로 파싱
    // 검증할 Dto앞에 @Validated
    @PostMapping
    public ResponseEntity<?> posts(@Validated @RequestBody ReplyPostDto dto
    , BindingResult result // 입력값 검증 결과 데이터를 갖고 있는 객체
    , HttpSession session
    ) {

        log.info("/api/v1/replies : POST");
        log.debug("parameter: {}", dto);

        if (result.hasErrors()) {
            Map<String, String> errors = makeValidationMessageMap(result);

            return ResponseEntity
                    .badRequest()
                    .body(errors);
        }

        boolean flag = replyService.register(dto, session);

        if (!flag) return ResponseEntity
                .internalServerError()
                .body("댓글 등록 실패!");

        return ResponseEntity
                .ok()
                .body(replyService.getReplies(dto.getBno(), new Page(1, 5)));
    }

    // 검증 오류 메시지 생성 함수
    // -> result에서 오류를 추출하여 필드 이름을 키로, 해당 오류 메시지를 값으로하는 Map 생성
    private Map<String, String> makeValidationMessageMap(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        // 에러정보가 모여있는 리스트
        List<FieldError> fieldErrors = result.getFieldErrors();

        for (FieldError error : fieldErrors) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return errors;
    }

    // 삭제 처리 요청
    @DeleteMapping("/{rno}")
    public ResponseEntity<?> delete(@PathVariable long rno) {

        ReplyListDto dtoList = replyService.remove(rno);

        return ResponseEntity
                .ok()
                .body(dtoList);
    }

    // 댓글 수정 요청
    // PUT, PATCH매핑 둘 다 받을 수 있게
    // @RequestBody : JSON 데이터 -> 자바 객체로 파싱
    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<?> modify(
            @Validated @RequestBody ReplyModifyDto dto
            , BindingResult result
    ) {

        log.info("/api/v1/replies : PUT, PATCH");
        log.debug("parameter: {}", dto);

        if (result.hasErrors()) {
            Map<String, String> errors = makeValidationMessageMap(result);

            return ResponseEntity
                    .badRequest()
                    .body(errors);
        }

        ReplyListDto replyListDto = replyService.modify(dto);

        return ResponseEntity.ok().body(replyListDto);

    }
}
