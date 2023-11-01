package kr.jc.chapter5.RepoTest;

import kr.jc.chapter5.Entity.Board;
import kr.jc.chapter5.Entity.Reply;
import kr.jc.chapter5.Repository.ReplyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepoTest {

    @Autowired
    ReplyRepository replyRepository;

    @Test
    void insertReply() {
        IntStream.rangeClosed(1,250).forEach(i -> {
            long bno = (long)(Math.random() * 100) + 1;
            Board board = Board.builder().bno(bno).build();
            Reply reply = Reply.builder()
                    .text("Reply:"+i)
                    .board(board)
                    .replyer("Guest")
                    .build();

            replyRepository.save(reply);
        });
    }
}
