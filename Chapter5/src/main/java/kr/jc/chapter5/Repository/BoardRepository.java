package kr.jc.chapter5.Repository;

import kr.jc.chapter5.Entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    /* 게시글 엔티티에서 연관 관계가 존재한 Member 엔티티의 데이터도 가져온다. */
    /* 예시의 쿼리문은, LEFT JOIN을 이용하여 해당 조건(bno)에 맞는 writer를 가져온다. */
    @Query("select b, w " +
            "from Board b left join b.writer w " +
            "where b.bno =:bno")
    Object getBoardWithWriter(@Param("bno") Long bno);

    /* 게시판 & 댓글을 함께 읽어오는 쿼리 메소드 */
    @Query("SELECT b, r " +
            "FROM Board b LEFT JOIN Reply r " +
            "ON r.board = b " +
            "WHERE b.bno = :bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);

    /* 게시판 & 유저 이름 & 댓글 수 가져오는 쿼리 메소드 */
    @Query(value="SELECT b, w, count(r)" +
            "FROM Board b " +
            "LEFT JOIN b.writer w " +
            "LEFT JOIN Reply r ON r.board = b " +
            "GROUP BY b",
    countQuery = "SELECT count(b) FROM Board b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable); // 목록 화면에 나타낼 데이터(게시물,작성자,댓글 수)들

    /* 조회 화면에 필요한 쿼리 메소드 구성 */
    @Query("SELECT b, w, count(r) " +
            "FROM Board b LEFT JOIN b.writer w " +
            "LEFT OUTER JOIN Reply r ON r.board = b " +
            "WHERE b.bno = :bno")
    Object getBoardByBno(@Param("bno") Long bno);
}
