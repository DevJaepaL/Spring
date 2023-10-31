package kr.jc.chapter5.Service;

import kr.jc.chapter5.DTO.BoardDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardServiceTests {
    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister() {
        BoardDTO dto = BoardDTO.builder()
                .title("DTO Test")
                .content("글 등록 Test")
                .writerEmail("User10@naver.com")
                .build();

        Long bno = boardService.register(dto);
    }
}
