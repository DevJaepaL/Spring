package kr.jc.chapter5.Repository;

import kr.jc.chapter5.Entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    @Modifying
    @Query("delete from Reply r " +
            "WHERE r.board.bno =:bno")
    void deleteByBno(Long bno);
}
