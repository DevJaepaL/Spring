package kr.ac.kopo.guestbook.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO, EN> {
    // DTO 리스트
    private List<DTO> dtoList;
    // 총 페이지 번호
    private int totalPage;
    // 현재 페이지 번호
    private int page;
    // 페이지 목록 사이즈
    private int size;
    // 시작, 끝 페이지 번호
    private int start, end;
    // 이전, 다음에 대한 내용
    private boolean prev, next;
    // 페이지 번호 목록
    private List<Integer> pageList;

    // 생성자
    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages(); // 총합 페이지를 가져온다. -> 전체 페이지 번호 개수 = 테이블의 Entity 개수
        makePageList(result.getPageable()); // 메소드를 통해 페이지 목록을 가져온다.
    }

    private void makePageList(Pageable pageable) {
        this.page = pageable.getPageNumber() + 1; // 0부터 시작하기 때문에 1 추가
        this.size = pageable.getPageSize();

        // Temp End Page
        int tempEnd = (int)(Math.ceil(page / 10.0)) * 10;
        start = tempEnd - 9;
        prev = start > 1;
        end = Math.min(totalPage, tempEnd);
        next = totalPage > tempEnd;

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }
}
