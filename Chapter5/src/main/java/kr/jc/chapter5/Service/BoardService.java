package kr.jc.chapter5.Service;

import kr.jc.chapter5.DTO.BoardDTO;
import kr.jc.chapter5.DTO.PageRequestDTO;
import kr.jc.chapter5.DTO.PageResultDTO;
import kr.jc.chapter5.Entity.Board;
import kr.jc.chapter5.Entity.Member;

public interface BoardService {
    Long register(BoardDTO dto);
    BoardDTO get(Long bno);
    void removeWithReplies(Long bno);
    void modify(BoardDTO boardDTO);
    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    default Board dtoToEntity(BoardDTO dto) {
        Member member = Member.builder().email(dto.getWriterEmail()).build();
        return Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member) // 전달받은 DTO의 작성자 데이터
                .build();
    }

    // BoardService 인터페이스에 추가하기 위한 Entity -> DTO
    default BoardDTO entityToDTO(Board board, Member member, Long replyCount) {
        return BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue()) // Long To Integer
                .build();
    }
}
