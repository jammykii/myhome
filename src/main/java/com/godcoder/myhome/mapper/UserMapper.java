package com.godcoder.myhome.mapper;

import com.godcoder.myhome.model.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    List<Account> getUsers(@Param("text") String text);

}
