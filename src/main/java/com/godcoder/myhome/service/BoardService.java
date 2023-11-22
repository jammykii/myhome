package com.godcoder.myhome.service;

import com.godcoder.myhome.model.Board;
import com.godcoder.myhome.model.Type;
import com.godcoder.myhome.model.Account;
import com.godcoder.myhome.repository.BoardRepository;
import com.godcoder.myhome.repository.TypeRepository;
import com.godcoder.myhome.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TypeRepository typeRepository;

    public Board save(String username, Board board, long board_types) {
        Account account = accountRepository.findByUsername(username);
        Type type = typeRepository.findById(board_types).orElse(null);
        board.setBoardType(type);
        board.setAccount(account);
        return boardRepository.save(board);
    }

}
