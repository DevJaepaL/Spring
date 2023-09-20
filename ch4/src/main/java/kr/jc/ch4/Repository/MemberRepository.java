package kr.jc.ch4.Repository;

import kr.jc.ch4.Entity.CommunityMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<CommunityMember, String> {
}
