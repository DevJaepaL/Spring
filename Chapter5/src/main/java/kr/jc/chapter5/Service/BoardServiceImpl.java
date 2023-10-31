package kr.jc.chapter5.Service;

import kr.jc.chapter5.DTO.BoardDTO;
import kr.jc.chapter5.Entity.Board;
import kr.jc.chapter5.Repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService{
    private final BoardRepository repository;   // Repo 객체 자동 주입
    @Override
    public Long register(BoardDTO dto) {
        log.info(dto);
        Board board = dtoToEntity(dto);
        repository.save(board);
        return board.getBno();
    }
}
