package kr.jc.chapter5.Repository;

import kr.jc.chapter5.Entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
