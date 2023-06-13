package kr.ac.kopo.guestbook.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import kr.ac.kopo.guestbook.dto.GuestbookDTO;
import kr.ac.kopo.guestbook.dto.PageRequestDTO;
import kr.ac.kopo.guestbook.dto.PageResultDTO;
import kr.ac.kopo.guestbook.entity.Guestbook;
import kr.ac.kopo.guestbook.entity.QGuestbook;
import kr.ac.kopo.guestbook.repository.GuestbookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService{

    private final GuestbookRepository repository;
    @Override
    public Long register(GuestbookDTO dto) {
        Guestbook entity = dtoToEntity(dto);
        log.info(entity);
        repository.save(entity);

        return entity.getGno();
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").ascending());
        BooleanBuilder booleanBuilder = getSearch(requestDTO); // 검색 조건 처리
        Page<Guestbook> result = repository.findAll(booleanBuilder, pageable); // 결과를 검색조건에 맞는 페이지를 보여준다.
        Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public GuestbookDTO read(Long gno) {
        Optional<Guestbook> result = repository.findById(gno);
        return result.isPresent()? entityToDto(result.get()): null;
    }

    @Override
    public void modify(GuestbookDTO dto) {
        Optional<Guestbook> result = repository.findById(dto.getGno());
        if(result.isPresent()){
            Guestbook entity = result.get();
            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());
            repository.save(entity);
        }
    }

    @Override
    public void remove(Long gno) {
        repository.deleteById(gno);
    }

    // Query DSL 처리로 게시글 검색하는 메소드
    private BooleanBuilder getSearch(PageRequestDTO requestDTO) {
        String type = requestDTO.getType();
        String keyword = requestDTO.getKeyword();
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QGuestbook qGuestbook = QGuestbook.guestbook;
        BooleanExpression expression = qGuestbook.gno.gt(0L); // gno(글 번호)가 1보가 큰 경우에만 생성한다.
        booleanBuilder.and(expression);

        // 검색 조건이 없는 경우
        if(type == null || type.trim().length() == 0) {
            System.out.println("현재 검색 조건 없음.");
            return booleanBuilder;
        }

        // 검색 조건 작성
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if(type.contains("t")) { // 게시판 글 제목 검색
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        } if(type.contains("c")) { // 게시판 글 내용 검색
            conditionBuilder.or(qGuestbook.content.contains(keyword));
        } if(type.contains("w")) { // 게시판 글 작성자 검색
            conditionBuilder.or(qGuestbook.writer.contains(keyword));
        } booleanBuilder.and(conditionBuilder); // 모든 조건을 통합한다.

        return booleanBuilder;
    }
}
