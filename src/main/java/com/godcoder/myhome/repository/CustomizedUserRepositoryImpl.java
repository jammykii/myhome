package com.godcoder.myhome.repository;

import com.godcoder.myhome.model.Account;
import com.godcoder.myhome.model.QUser;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class CustomizedUserRepositoryImpl implements CustomizedUserRepository {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Account> findByUsernameCustom(String username) {

        QUser qUser = QUser.user;
        JPAQuery<?> query = new JPAQuery<Void>(em);
        List<Account> accounts = query.select(qUser)
                .from(qUser)
                .where(qUser.username.contains(username))
                .fetch();
        return accounts;
    }

    @Override
    public List<Account> findByUsernameJdbc(String username) {
        List<Account> accounts = jdbcTemplate.query(
                "SELECT * FROM account WHERE username like ?",
                new Object[]{"%" + username + "%"},
                new BeanPropertyRowMapper(Account.class));
        return accounts;
    }
}
