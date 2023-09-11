package com.godcoder.myhome.repository;

import com.godcoder.myhome.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type, Long> {

    @Override
    List<Type> findAll();

    Type findByTypeName(String typeName);
}
