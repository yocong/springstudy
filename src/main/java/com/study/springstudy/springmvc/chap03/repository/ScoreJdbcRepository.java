package com.study.springstudy.springmvc.chap03.repository;

import com.study.springstudy.springmvc.chap03.entity.Score;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// @Repository = @Component와 의미 + 저장소의 개념 추가
// ScoreJdbcRepository의 객체생성 제어권을 스프링에게 넘김
@Repository
public class ScoreJdbcRepository implements ScoreRepository {

    // DB 연결
    private String url = "jdbc:mariadb://localhost:3307/spring5";
    private String username = "root";
    private String password = "mariadb";

    public ScoreJdbcRepository() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 저장소에 데이터 추가하는 기능
    @Override
    public boolean save(Score score) {

        try (Connection conn = connect()) {

            String sql = "INSERT INTO tbl_score " +
                    "(stu_name, kor, eng, math, total, average, grade) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, score.getStuName());
            pstmt.setInt(2, score.getKor());
            pstmt.setInt(3, score.getEng());
            pstmt.setInt(4, score.getMath());
            pstmt.setInt(5, score.getTotal());
            pstmt.setDouble(6, score.getAverage());
            pstmt.setString(7, score.getGrade().toString());

            int result = pstmt.executeUpdate(); // SQL 쿼리 실행

            if (result == 1) return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 저장소에서 데이터 전체조회하는 기능
    @Override
    public List<Score> findAll(String sort) {

        List<Score> scoreList = new ArrayList<>();
        try (Connection conn = connect()) {

            // sortCondition(sort) : 조건에 맞는 ORDER BY 추가
            String sql = "SELECT * FROM tbl_score " + sortCondition(sort);

            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery(); // 정보 조회 실행

            while(rs.next()) { // 정보가 조회될 때 마다 scoreList에 추가
                Score score = new Score(rs); // 정보의 내용 Score에서 처리
                scoreList.add(score);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return scoreList;
    }

    private String sortCondition(String sort) {
        String sortSql = "ORDER BY ";
        switch (sort) {
            case "num":
                sortSql += "stu_num";
                break;
            case "name":
                sortSql += "stu_name";
                break;
            case "avg":
                sortSql += "average DESC";
                break;
        }
        return sortSql;
    }

    // 저장소에서 데이터 개별조회하는 기능 (학번으로 구분)
    @Override
    public Score findOne(long stuNum) {

        try (Connection conn = connect()) {
            String sql = "SELECT * FROM tbl_score WHERE stu_num =?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, stuNum);
            ResultSet rs = pstmt.executeQuery(); // 조회

            while(rs.next()) {
                Score score = new Score(rs); // Score에서 처리
                return score;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(long stuNum) {


        try(Connection conn = connect()){
            String sql = "DELETE FROM tbl_score WHERE stu_num =?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, stuNum);

            int result = pstmt.executeUpdate();

            if(result == 1) return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // db연결 메서드
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
