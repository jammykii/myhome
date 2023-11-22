package com.godcoder.myhome.service;

import com.godcoder.myhome.model.Account;
import com.godcoder.myhome.model.Board;
import com.godcoder.myhome.model.Role;
import com.godcoder.myhome.repository.BoardRepository;
import com.godcoder.myhome.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public Account save(Account account) {
        String encodedpassword = encodedpassword = passwordEncoder.encode(account.getPassword());

        account.setPassword(encodedpassword);
        account.setEnabled(true);

        Role role = new Role();

        role.setId(1);

        account.getRoles().add(role);

        Account savedAccount = accountRepository.save(account);

        Board board = new Board();
        board.setTitle("안녕하세요!");
        board.setContent("반갑습니다.");
        board.setBoard_type(4);
        board.setAccount(savedAccount);
        boardRepository.save(board);

        return savedAccount;
    }

    public boolean checkUserNameDuplicate(String name){
        return accountRepository.existsByUsername(name);
    }
}
