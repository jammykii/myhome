package com.godcoder.myhome.service;

import com.godcoder.myhome.model.Board;
import com.godcoder.myhome.model.Type;
import com.godcoder.myhome.model.User;
import com.godcoder.myhome.repository.BoardRepository;
import com.godcoder.myhome.repository.TypeRepository;
import com.godcoder.myhome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TypeRepository typeRepository;

    public Board save(String username, Board board, long board_types) {
        User user = userRepository.findByUsername(username);
        Type type = typeRepository.findById(board_types).orElse(null);
        System.out.println(board_types);
        board.setBoardType(type);
        board.setUser(user);
        return boardRepository.save(board);
    }

}
