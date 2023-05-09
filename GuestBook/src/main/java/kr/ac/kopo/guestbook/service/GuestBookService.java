package kr.ac.kopo.guestbook.service;

import kr.ac.kopo.guestbook.Entity.GuestBook;
import kr.ac.kopo.guestbook.dto.GuestBookDTO;
import kr.ac.kopo.guestbook.dto.PageRequestDTO;
import kr.ac.kopo.guestbook.dto.PageResultDTO;

public interface GuestBookService {
    // 방명록 등록
    Long register(GuestBookDTO dto);
    PageResultDTO<GuestBookDTO, GuestBook> getList(PageRequestDTO requestDTO);

    // DTO 를 Entity로 변환해야 한다.
    default GuestBook dtoToEntity(GuestBookDTO dto) {
        GuestBook entity = GuestBook.builder()
                .gNo(dto.getGNo())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }

    default GuestBookDTO entityToDto(GuestBook entity) {
        GuestBookDTO dto = GuestBookDTO.builder()
                .gNo(entity.getGNo())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getRegDate())
                .build();

        return dto;
    }
}
