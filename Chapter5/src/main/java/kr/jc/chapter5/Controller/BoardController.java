package kr.jc.chapter5.Controller;

import kr.jc.chapter5.ServiceTest.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board/") // Mapping 주소(API)
@Log4j2
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
}
