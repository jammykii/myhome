package com.godcoder.myhome.controller;

import com.godcoder.myhome.mapper.UserMapper;
import com.godcoder.myhome.model.Account;
import com.godcoder.myhome.model.Board;
import com.godcoder.myhome.model.QUser;
import com.godcoder.myhome.repository.BoardRepository;
import com.godcoder.myhome.repository.AccountRepository;
import com.godcoder.myhome.repository.UserRoleRepository;
import com.godcoder.myhome.service.AccountService;
import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
class UserApiController {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private AccountService accountService;


    @Autowired
    private UserMapper userMapper;

    @GetMapping("/users")
    Iterable<Account> all(@RequestParam(required = false) String method, @RequestParam(required = false) String text) {
        Iterable<Account> users = null;


        if("query".equals(method)){
            users = repository.findByUsernameQuery(text);
        }else if("nativeQuery".equals(method)){
            users = repository.findByUsernameNativeQuery(text);
        }else if("querydsl".equals(method)){
            QUser user = QUser.user;
            Predicate predicate = user.username.contains(text);
            users = repository.findAll(predicate);
        }else if("querydslCustom".equals(method)){
            users = repository.findByUsernameCustom(text);
        }else if("jdbc".equals(method)){
            users = repository.findByUsernameJdbc(text);
        }else if("mybatis".equals(method)){
            users = userMapper.getUsers(text);
        }else{
            users = repository.findAll();
        }
        return users;
    }

    @GetMapping("/username/{username}/exists")
    public ResponseEntity<Boolean> checkUserName(@PathVariable String username){
        log.debug("1");
        System.out.println(ResponseEntity.ok(accountService.checkUserNameDuplicate(username)));
        return ResponseEntity.ok(accountService.checkUserNameDuplicate(username));
    }

    @PostMapping("/users")
    Account newUser(@RequestBody Account newAccount) {
        return repository.save(newAccount);
    }

    // Single item
    @GetMapping("/users/{id}")
    Account one(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @GetMapping("/usersName/{username}")
    Account userName(@PathVariable String username){
        return repository.findByUsername(username);
    }

    @PutMapping("/users/{id}")
    Account replaceUser(@RequestBody Account newAccount, @PathVariable Long id) {

        return repository.findById(id)
                .map(user -> {
                    user.getBoards().clear();
                    user.getBoards().addAll(newAccount.getBoards());
                    for (Board board : user.getBoards()){
                        board.setAccount(user);
                    }
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newAccount.setId(id);
                    return repository.save(newAccount);
                });
    }



    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        Account account = repository.findById(id).orElse(null);
        Board boards = boardRepository.findByAccount(account);

        userRoleRepository.deleteByUserId(account.getId());
        boardRepository.deleteById(boards.getId());
        repository.deleteById(id);

    }
}
