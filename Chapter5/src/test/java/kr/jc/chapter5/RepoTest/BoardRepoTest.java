package kr.jc.chapter5.RepoTest;

import kr.jc.chapter5.Entity.Board;
import kr.jc.chapter5.Entity.Member;
import kr.jc.chapter5.Repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepoTest {
    @Autowired
    BoardRepository boardRepository;

    @Test
    public void insertBoard() {
        IntStream.rangeClosed(1,100).forEach(i -> {
            Member member = Member.builder().email("exampleUser"+i+"@gmail.com").build();
            Board board = Board.builder()
                    .title("Title:"+i)
                    .content("exContent:"+i)
                    .writer(member)
                    .build();
            boardRepository.save(board);
        });
    }

    @Test
    public void testGetBoardWithReply() {
        List<Object[]> results = boardRepository.getBoardWithReply(100L);
        for(Object[] arr : results) {
            System.out.println(Arrays.toString(arr));
        }
    }

    @Test
    public void testWithReplyCount() {
        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").ascending());
        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);

        result.get().forEach(row -> {
            Object[] arr = (Object[]) row;
            System.out.println(Arrays.toString(arr));
        });
    }

    @Test
    public void testReadBoard() {
        Object result = boardRepository.getBoardByBno(50L);
        Object[] arr = (Object[]) result;
        System.out.println(Arrays.toString(arr));
    }
}
