package com.study.springstudy.springmvc.chap05.entity;

/*

-- 조회수 기록 관리 테이블
CREATE TABLE view_log (
	id INT PRIMARY KEY auto_increment,
    account VARCHAR(50),
    board_no INT,
    view_time DATETIME
);

ALTER TABLE view_log
ADD CONSTRAINT fk_member_viewlog
FOREIGN KEY (account)
REFERENCES tbl_member (account);

ALTER TABLE view_log
ADD CONSTRAINT fk_board_viewlog
FOREIGN KEY (board_no)
REFERENCES tbl_board (board_no);

SELECT * FROM view_log;
 */

import lombok.*;

import java.time.LocalDateTime;

@Setter @Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewLog {

    private long id;
    private String account;
    private long boardNo;
    private LocalDateTime viewTime;
}
