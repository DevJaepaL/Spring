package kr.jc.chapter5.ServiceTest;

import jakarta.transaction.Transactional;
import kr.jc.chapter5.DTO.BoardDTO;
import kr.jc.chapter5.DTO.PageRequestDTO;
import kr.jc.chapter5.DTO.PageResultDTO;
import kr.jc.chapter5.Entity.Board;
import kr.jc.chapter5.Entity.Member;
import kr.jc.chapter5.Repository.BoardRepository;
import kr.jc.chapter5.Repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    @Override
    public Long register(BoardDTO dto) {
        log.info(dto);
        Board board = dtoToEntity(dto);
        boardRepository.save(board);
        return board.getBno();
    }
    // 게시물 조회 메소드
    @Override
    public BoardDTO get(Long bno) {
        Object result = boardRepository.getBoardByBno(bno);
        Object[] arr = (Object[]) result;
        return entityToDTO((Board) arr[0], (Member) arr[1], (Long) arr[2]);
    }

    @Transactional
    @Override
    public void removeWithReplies(Long bno) {
        replyRepository.deleteByBno(bno);
        boardRepository.deleteById(bno);
    }

    @Transactional // 서비스 메소드 전체가 하나의 트랜잭션(연산)임을 명시한다.
    @Override
    public void modify(BoardDTO boardDTO) {
        /*  findById 메소드와는 다르게 getReferenceById의 경우,
            실제 엔티티를 참조하기 직전까지 DB 접근하지 않으며 프록시를 이용해 메소드를 진행합니다. */
        Board board = boardRepository.getReferenceById(boardDTO.getBno());

        if(board != null) {
            board.changeTitle(boardDTO.getTitle());
            board.changeContent(boardDTO.getContent());
            boardRepository.save(board);
        }
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        log.info(pageRequestDTO);

        Function<Object[], BoardDTO> fn = (en -> entityToDTO( (Board)en[0], (Member)en[1], (Long) en[2]) );
        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageRequestDTO.getPageable(Sort.by("bno").ascending()));

        return new PageResultDTO<>(result,fn); // 페이지와 목록에 필요한 각 결과값들을 반환한다.
    }
}
