package com.godcoder.myhome.service;

import com.godcoder.myhome.model.Board;
import com.godcoder.myhome.model.Role;
import com.godcoder.myhome.model.User;
import com.godcoder.myhome.repository.BoardRepository;
import com.godcoder.myhome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ListIterator;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public User save(User user) {
        String encodedpassword = encodedpassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedpassword);
        user.setEnabled(true);

        Role role = new Role();

        role.setId(1);
        user.getRoles().add(role);

        User savedUser = userRepository.save(user);

        Board board = new Board();
        board.setTitle("안녕하세요!");
        board.setContent("반갑습니다.");
        board.setBoard_type(4);
        board.setUser(savedUser);
        boardRepository.save(board);

        return savedUser;
    }
}
