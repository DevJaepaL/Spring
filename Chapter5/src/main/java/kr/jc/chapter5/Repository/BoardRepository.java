package kr.jc.chapter5.Repository;

import kr.jc.chapter5.Entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,  Long> {
    /* 게시글의 PK 값(bno) 읽기. */
    @Query( "SELECT b, w, count(r) " +
            " FROM Board b LEFT JOIN b.writer w " +
            " LEFT OUTER JOIN Reply r ON r.board = b" +
            " WHERE b.bno = :bno")
    Object getBoardByBno(@Param("bno") Long bno);

    /* Board를 사용하고 있으나, Member를 같이 조회해야하는 상태이다. */
    @Query("select b, w from Board b left join b.writer w where b.bno =:bno")
    Object getBoardWithWriter(@Param("bno") Long bno);

    // 하나의 게시글에 있는 모든 댓글을 조회하는 인터페이스 메소드
    @Query("SELECT b, r FROM Board b LEFT JOIN Reply r ON r.board = b WHERE b.bno = :bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);

    // 목록 화면에 하나의 게시글의 댓글 개수를 나타내기 위한 JPQL 메소드
    @Query(value =  "SELECT b, w, count(r) " +
                    "FROM Board b " +
                    "LEFT JOIN b.writer w " +
                    "LEFT JOIN Reply r ON r.board = b " +
                    "GROUP BY b",
                    countQuery = "SELECT count(b) FROM Board b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable); // 목록 화면에 필요한 데이터.
}
