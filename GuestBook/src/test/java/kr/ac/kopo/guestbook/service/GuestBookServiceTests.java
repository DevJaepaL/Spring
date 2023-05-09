package kr.ac.kopo.guestbook.service;

import kr.ac.kopo.guestbook.Entity.GuestBook;
import kr.ac.kopo.guestbook.dto.GuestBookDTO;
import kr.ac.kopo.guestbook.dto.PageRequestDTO;
import kr.ac.kopo.guestbook.dto.PageResultDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GuestBookServiceTests {

    @Autowired
    private GuestBookService service;

    // DTO를 이용한 레지스터 테스트
    @Test
    public void testRegister() {
        GuestBookDTO guestBookDTO = GuestBookDTO.builder()
                .title("샘플 제목")
                .content(" 샘플 내용")
                .writer("테스트 작성자")
                .build();
        System.out.println(service.register(guestBookDTO));
    }
    /** List 처리 테스트 */
    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();

        PageResultDTO<GuestBookDTO, GuestBook> resultDTO = service.getList(pageRequestDTO);

        for(GuestBookDTO dto : resultDTO.getDtoList()) {
            System.out.println(dto);
        }
    }
}
