package kr.jc.ch4.Repository;

import kr.jc.ch4.Entity.Comment;
import kr.jc.ch4.Entity.UserBoard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class CommentRepoTests {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void insertComment() {

        IntStream.rangeClosed(1,300).forEach(i -> {
            // 1-100의 게시글 번호 랜덤 접근.
            long boardNo = (long)(Math.random() * 100) + 1;

            UserBoard board = UserBoard.builder()
                    .boardNo(boardNo)
                    .build();

            Comment comment = Comment.builder()
                    .comment("Comment : " + i)
                    .userBoard(board)
                    .replyer("Anonymous Guest")
                    .build();

            commentRepository.save(comment);
        });
    }
}
