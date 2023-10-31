package kr.jc.chapter5.Repository;

import kr.jc.chapter5.Entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,  Long> {
}
