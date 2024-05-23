package com.study.springstudy.springmvc.chap05.entity;

/*
-- 댓글 테이블 생성
CREATE TABLE tbl_reply (
	reply_no INT(8) PRIMARY KEY auto_increment,
    reply_text VARCHAR(1000) NOT NULL,
    reply_writer VARCHAR(100) NOT NULL,
    reply_date DATETIME default current_timestamp,
    board_no INT(8),
    constraint fk_reply
    foreign key (board_no)
    references tbl_board (board_no)
    on delete cascade
);
 */

import lombok.*;

import java.time.LocalDateTime;

@Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reply {

    private long replyNo;
    @Setter
    private String replyText;
    private String replyWriter;
    private LocalDateTime replyDate;
    private long boardNo;

}
