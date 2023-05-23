package kr.ac.kopo.guestbook.controller;

import kr.ac.kopo.guestbook.dto.GuestBookDTO;
import kr.ac.kopo.guestbook.dto.PageRequestDTO;
import kr.ac.kopo.guestbook.service.GuestBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor // 자동 주입을 위한 어노테이션
public class GuestbookController {
    private final GuestBookService service;

    /** 실행 시 최초 웹 경로 */
    @GetMapping("/")
    public String index() {
        return "redirect:/guestbook/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("페이지 요청 정보 : " + pageRequestDTO);
        // 결과 데이터를 "result"라는 속성에 담아 화면에 전달하기 위한 Model
        model.addAttribute("result", service.getList(pageRequestDTO));
    }

    /** 게시글을 등록하는 Method */
    @GetMapping("/register")
    public void register() {
        log.info("등록 테스트");
    }

    @PostMapping("/register")
    public String registerPost(GuestBookDTO dto, RedirectAttributes redirectAttributes) {
        log.info("DTO : " + dto);
        // 새로운 Entity 번호를 추가해준다.
        Long gNo = service.register(dto);
        redirectAttributes.addFlashAttribute("msg", gNo);

        // 리스트 화면으로 돌아간다.
        return "redirect:/guestbook/list";
    }
}
