package kr.jc.ch4.Repository;

import kr.jc.ch4.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

// 상속받는 JPA Repository의 제네릭 타입은, PK(기본 키)로 지정 한다.
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
