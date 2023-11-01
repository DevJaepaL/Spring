package kr.jc.chapter5.ServiceTest;

import kr.jc.chapter5.DTO.BoardDTO;
import kr.jc.chapter5.DTO.PageRequestDTO;
import kr.jc.chapter5.DTO.PageResultDTO;
import kr.jc.chapter5.Entity.Board;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegist() {
        BoardDTO dto = BoardDTO.builder()
                .title("Test Title")
                .content("등록 메소드 테스트")
                .writerEmail("exampleUser9@gmail.com")
                .build();

        Long bno = boardService.register(dto);
    }

    @Test
    public void testServiceList() {

        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);

        for(BoardDTO boardDTO : result.getDtoList()) {
            System.out.println(boardDTO);
        }
    }

    @Test
    public void testGet() {
        Long bno = 11L;
        BoardDTO boardDTO = boardService.get(bno);
        System.out.println(boardDTO);
    }

    @Test
    public void testDelete() {
        Long bno = 11L;
        boardService.removeWithReplies(bno);
    }

    @Test
    public void testModify() {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(7L)
                .title("내용7")
                .content("내용 변경")
                .build();

        boardService.modify(boardDTO);
    }
}
