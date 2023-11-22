package com.godcoder.myhome.repository;

import com.godcoder.myhome.model.Account;

import java.util.List;

public interface CustomizedUserRepository {
    List<Account> findByUsernameCustom(String username);
    List<Account> findByUsernameJdbc(String username);
}
