package kr.jc.ch4.Repository;

import kr.jc.ch4.Entity.CommunityMember;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepoTests {

    @Autowired
    private MemberRepository memberRepository;

    // 임의의 더미 데이터 100개 작성
    @Test
    public void insertMeberDatas() {
        // 100개의 데이터 생성
        IntStream.rangeClosed(1,100).forEach(i -> {
            CommunityMember member = CommunityMember.builder()
                    .userEmail("testUser"+i+"@test.com")
                    .password("testPwd123")
                    .userName("TEST_USER"+i)
                    .build();
            // DB에 저장
            memberRepository.save(member);
        });
    }
}
