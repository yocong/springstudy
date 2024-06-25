package com.study.springstudy.springmvc.chap05;

import com.study.springstudy.springmvc.chap04.mapper.BoardMapper;
import com.study.springstudy.springmvc.chap05.entity.Reply;
import com.study.springstudy.springmvc.chap05.mapper.ReplyMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ReplyMapperTest {

    @Autowired BoardMapper boardMapper;
    @Autowired
    ReplyMapper replyMapper;

//    @Test
//    @DisplayName("")
//    void bulkInsert() {
//        // 게시물 100개와 댓글 5000개를 랜덤으로 등록
//        for (int i = 1; i <= 100; i++) {
//            Board b = Board.builder()
//                    .title("재밌는 글" + i)
//                    .content("응 개노잼이야~~" + i)
//                    .writer("아무무나" + i)
//                    .build();
//
//            boardMapper.save(b);
//        }
//
//        for (int i = 1; i <= 5000; i++) {
//            Reply reply = Reply.builder()
//                    .replyText("하하호호댓글" + i)
//                    .replyWriter("꾸러긔" + i)
//                    .boardNo((long) (Math.random() * 100 + 1))
//                    .build();
//
//            replyMapper.save(reply);
//        }
//    }

//    @Test
//    @DisplayName("전체조회")
//    void findAllTest() {
//        //given
//        long boardNo = 1;
//        //when
//        List<Reply> replies = replyMapper.findAll(boardNo, null);
//        //then
//        replies.forEach(System.out::println);
//    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteTest() {
        //given
        long replyNo = 1;
        //when
        replyMapper.delete(replyNo);
        //then
    }
    
    @Test
    @DisplayName("댓글 수정")
    void modifyTest() {
        //given
        long replyNo = 2;
        Reply reply = Reply.builder()
                .replyNo(replyNo)
                .replyText("수정수정")
                .build();
        //when
        replyMapper.modify(reply);
        //then
    }
    
}