package kr.jc.chapter5.Service;

import kr.jc.chapter5.DTO.BoardDTO;
import kr.jc.chapter5.Entity.Board;
import kr.jc.chapter5.Entity.Member;

public interface BoardService {
    Long register(BoardDTO dto);
    default Board dtoToEntity(BoardDTO dto) {
        Member member = Member.builder().email(dto.getWriterEmail()).build();

        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();

        return board;
    }
}
