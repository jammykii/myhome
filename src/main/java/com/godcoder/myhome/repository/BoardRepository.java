package com.godcoder.myhome.repository;

import com.godcoder.myhome.dto.BoardCountRankDTO;
import com.godcoder.myhome.dto.UserRoleApiDTO;
import com.godcoder.myhome.model.Board;
import com.godcoder.myhome.model.Type;
import com.godcoder.myhome.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByTitle(String title);
    List<Board> findByTitleOrContent(String title, String content);
    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
    Page<Board> findByBoardTypeOrUser(Type type, User user,Pageable pageable);

    public static final String BoardCountRank =
            "SELECT ROW_NUMBER() over (ORDER BY COUNT(b.id) desc) AS Rank, u.id AS id, u.username AS username, COUNT(b.id) AS count " +
                    "FROM board b, `user` u WHERE b.user_id = u.id " +
                    "GROUP BY u.id, u.username ORDER BY 1 asc";

    @Query(value = BoardCountRank, nativeQuery = true)
    List<BoardCountRankDTO> BoardRank();
}
