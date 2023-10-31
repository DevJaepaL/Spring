package kr.jc.chapter5.Repository;

import kr.jc.chapter5.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}
