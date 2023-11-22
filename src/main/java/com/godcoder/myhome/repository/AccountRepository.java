package com.godcoder.myhome.repository;

import com.godcoder.myhome.model.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long>, QuerydslPredicateExecutor<Account>, CustomizedUserRepository {
    @EntityGraph(attributePaths = { "boards" })
    List<Account> findAll();

    Account findByUsername(String username);

    @Query("select a from Account a where a.username like %?1%")
    List<Account> findByUsernameQuery(String username);

    @Query(value = "select * from Account a where a.username like %?1%", nativeQuery = true)
    List<Account> findByUsernameNativeQuery(String username);

    boolean existsByUsername(String username);
}
