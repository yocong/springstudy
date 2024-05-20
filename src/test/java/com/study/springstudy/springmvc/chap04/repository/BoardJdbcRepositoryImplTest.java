package com.study.springstudy.springmvc.chap04.repository;

import com.study.springstudy.springmvc.chap04.entity.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class BoardRepositoryImplTest {

    @Autowired
    BoardRepository repository;

    // 테스트 전에 실행할 코드
    @BeforeEach
    void bulkInsert() {
        for (int i = 1; i <= 3; i++) {
            Board b = new Board();
            b.setTitle("테스트제목"+i);
            b.setWriter("글쓴이"+i);
            b.setContent("내용"+i);

            repository.save(b);
        }
    }


    @Test
    @DisplayName("전체조회 테스트를 수행하면 게시물의 개수가 3개이다.")
    void findAll() {
        //given

        //when
        List<Board> boardList = repository.findAll();
        //then
        boardList.forEach(System.out::println);

        assertEquals(3, boardList.size());
    }


    @Test
    @DisplayName("새로운 게시글 데이터를 삽입하면 전체결과가 4건이 된다.")
    void saveTest() {
        //given
        Board b = new Board();
        b.setContent("새로운 내용");
        b.setTitle("새로운 제목");
        b.setWriter("새로운 글쓴이");
        //when
        boolean flag = repository.save(b);
        //then
        assertTrue(flag);
        assertEquals(4, repository.findAll().size());
    }

    @Test
    @DisplayName("글번호가 1번인 게시물을 삭제할 수 있다.")
    void deleteTest() {
        //given
        int boardNo = repository.findAll().get(0).getBoardNo();
        //when
        boolean flag = repository.delete(boardNo);
        //then
        assertTrue(flag);
        assertEquals(2, repository.findAll().size());
    }


}