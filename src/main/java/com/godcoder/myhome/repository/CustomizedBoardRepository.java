package com.godcoder.myhome.repository;

import com.godcoder.myhome.model.Board;

import java.util.List;

public interface CustomizedBoardRepository {
    List<Board> findByUsernameJdbc(String username);
}














