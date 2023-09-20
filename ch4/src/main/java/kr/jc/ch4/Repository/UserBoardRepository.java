package kr.jc.ch4.Repository;

import kr.jc.ch4.Entity.UserBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBoardRepository extends JpaRepository<UserBoard, Long> {
}
