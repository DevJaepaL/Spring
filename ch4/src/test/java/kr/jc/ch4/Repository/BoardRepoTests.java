package kr.jc.ch4.Repository;

import kr.jc.ch4.Entity.CommunityMember;
import kr.jc.ch4.Entity.UserBoard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepoTests {
    @Autowired
    private UserBoardRepository userBoardRepository;

    @Test
    public void insertUserBoard() {
        IntStream.rangeClosed(1,100).forEach(i->{
            CommunityMember member = CommunityMember.builder()
                    .userEmail("testUser"+i+"@test.com")
                    .build();

            UserBoard board = UserBoard.builder()
                    .boardTitle("Title : "+ i)
                    .content("Content : "+ i)
                    .writer(member)
                    .build();

            userBoardRepository.save(board);
        });
    }
}
