package com.godcoder.myhome.repository;

import com.godcoder.myhome.dto.BoardCountRankDTO;
import com.godcoder.myhome.model.Account;
import com.godcoder.myhome.model.Board;
import com.godcoder.myhome.model.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByTitle(String title);
    List<Board> findByTitleOrContent(String title, String content);
    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
    Page<Board> findByBoardTypeOrAccount(Type type, Account account, Pageable pageable);

    Board findByAccount(Account account);

    public static final String BoardCountRank =
            "SELECT ROW_NUMBER() over (ORDER BY COUNT(b.id) desc) AS Rank, a.id AS id, a.username AS username, COUNT(b.id) AS count " +
                    "FROM board b, Account a WHERE b.user_id = a.id " +
                    "GROUP BY u.id, a.username ORDER BY 1 asc";

    @Query(value = BoardCountRank, nativeQuery = true)
    List<BoardCountRankDTO> BoardRank();
}
