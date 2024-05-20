package com.study.springstudy.springmvc.chap03.repository;

import com.study.springstudy.springmvc.chap03.entity.Score;

import java.util.List;

public class ScoreMemoryRepository implements ScoreRepository {
    @Override
    public boolean save(Score score) {
        return false;
    }

    @Override
    public List<Score> findAll(String sort) {
        return List.of();
    }

    @Override
    public Score findOne(long stuNum) {
        return null;
    }

    @Override
    public int[] findRankByStuNum(long stuNum) {
        return new int[0];
    }
}