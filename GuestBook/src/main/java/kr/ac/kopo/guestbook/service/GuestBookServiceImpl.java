package kr.ac.kopo.guestbook.service;

import kr.ac.kopo.guestbook.Entity.GuestBook;
import kr.ac.kopo.guestbook.Repository.GuestBookRepository;
import kr.ac.kopo.guestbook.dto.GuestBookDTO;
import kr.ac.kopo.guestbook.dto.PageRequestDTO;
import kr.ac.kopo.guestbook.dto.PageResultDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;


// 빈 처리
@Service
@Log4j2
@RequiredArgsConstructor
public class GuestBookServiceImpl implements GuestBookService{
    private final GuestBookRepository repository;
    @Override
    public Long register(GuestBookDTO dto) {
        GuestBook entity = dtoToEntity(dto);
        log.info(entity);
        repository.save(entity);
        return entity.getGNo();
    }

    // 페이지에 대한 DTO 결과를 얻어내는 메소드
    @Override
    public PageResultDTO<GuestBookDTO, GuestBook> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("gNo").descending());

        Page<GuestBook> result = repository.findAll(pageable);
        Function<GuestBook, GuestBookDTO> fn = (entity -> entityToDto(entity)); // Entity To Dto 변환

        return new PageResultDTO<>(result, fn);
    }
}
