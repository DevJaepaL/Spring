package kr.jc.chapter5.Controller;

import kr.jc.chapter5.DTO.PageRequestDTO;
import kr.jc.chapter5.ServiceTest.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board/") // Mapping 주소(API)
@Log4j2
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("--------- List ---------");
        model.addAttribute("result", boardService.getList(pageRequestDTO));
    }
}
