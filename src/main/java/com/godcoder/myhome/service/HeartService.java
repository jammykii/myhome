package com.godcoder.myhome.service;

import com.godcoder.myhome.model.Account;
import com.godcoder.myhome.model.Board;
import com.godcoder.myhome.model.Heart;
import com.godcoder.myhome.repository.BoardRepository;
import com.godcoder.myhome.repository.HeartRepository;
import com.godcoder.myhome.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class HeartService {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private HeartRepository heartRepository;

    @Transactional
    public void insert(long user_id, long board_id) throws Exception {
        Account account = accountRepository.findById(user_id).orElse(null);
        Board board = boardRepository.findById(board_id).orElse(null);

        if(Objects.isNull(heartRepository.findByUserIdAndBoardId(user_id, board_id))){
            throw new Exception();
        }

        Heart heart = new Heart();
        
        heart.setUserId(user_id);
        heart.setBoardId(board_id);
        heart.setBoard(board);
        heart.setAccount(account);
        heartRepository.save(heart);
    }

    @Transactional
    public void delete(long user_id, long board_id) throws Exception {

        Account account = accountRepository.findById(user_id).orElse(null);
        Board board = boardRepository.findById(board_id).orElse(null);

        Heart heart = heartRepository.findByUserIdAndBoardId(user_id, board_id);

        heartRepository.delete(heart);
    }
}
