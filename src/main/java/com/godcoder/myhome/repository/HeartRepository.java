package com.godcoder.myhome.repository;

import com.godcoder.myhome.model.Heart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    Heart findByUserIdAndBoardId(long userId, long boardId);

}
