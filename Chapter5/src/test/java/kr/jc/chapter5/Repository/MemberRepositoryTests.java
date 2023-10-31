package kr.jc.chapter5.Repository;

import kr.jc.chapter5.Entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {
    // DB 테스트 코드 작성
    @Autowired
    private MemberRepository memberRepository;

    // DB에 더미 데이터 100개 생성
    @Test
    public void insertMembers() {
        IntStream.rangeClosed(1,100).forEach(i -> {
            Member member = Member.builder()
                    .email("User"+i+"@naver.com")
                    .password("1234")
                    .name("exName"+i)
                    .build();
            memberRepository.save(member);
        });
    }
}
