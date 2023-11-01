package kr.jc.chapter5.RepoTest;

import kr.jc.chapter5.Entity.Member;
import kr.jc.chapter5.Repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepoTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertMemberToDB() {
        IntStream.rangeClosed(1,100).forEach(i -> {
            Member member = Member.builder()
                    .email("exampleUser"+i+"@gmail.com")
                    .password("qwer1234")
                    .name("exUserName"+i)
                    .build();
            memberRepository.save(member);
        });
    }
}
