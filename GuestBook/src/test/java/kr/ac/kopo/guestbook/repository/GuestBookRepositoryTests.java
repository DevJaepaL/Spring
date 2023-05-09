package kr.ac.kopo.guestbook.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import kr.ac.kopo.guestbook.Entity.GuestBook;
import kr.ac.kopo.guestbook.Entity.QGuestBook;
import kr.ac.kopo.guestbook.Repository.GuestBookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestBookRepositoryTests {

    @Autowired
    private GuestBookRepository guestBookRepository;

    @Test
    public void insertDummies() {
        IntStream.rangeClosed(1,300).forEach(i -> {
            GuestBook guestBook = GuestBook.builder()
                    .title("제목 : " + i)
                    .content("내용 : " + i)
                    .writer("유저 : " + (i % 10))
                    .build();
            System.out.println(guestBookRepository.save(guestBook));
        });
    }

    /** 업데이트 테스트 */
    @Test
    public void updateTest() {
        Optional<GuestBook> result = guestBookRepository.findById(300L); // 번호가 300인 ID 탐색
        if(result.isPresent()) {
            GuestBook guestBook = result.get(); // Entity 접근
            guestBook.changeTitle("타이틀 변경 메소드 테스트");
            guestBook.changeContent("콘텐츠(내용) 변경 메소드 테스트");

            guestBookRepository.save(guestBook); // Entity 업데이트 반영 내용 저장
        }
    }
    /** 단일 항목 검색 테스트 */
    @Test
    public void testQuery() {
        // 10개의 데이터에 대한 페이징 처리
        Pageable pageable = PageRequest.of(0,10, Sort.by("gNo").descending());
        QGuestBook qGuestBook = QGuestBook.guestBook;
        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = qGuestBook.title.contains(keyword);
        builder.and(expression);

        Page<GuestBook> result = guestBookRepository.findAll(builder, pageable);
        result.stream().forEach(guestBook -> {
            System.out.println(guestBook);
        });
    }

    @Test
    /** 다중 항목 검색 테스트 */
    public void testQuery2() {
        Pageable pageable = PageRequest.of(0,10,Sort.by("gNo").descending());
        QGuestBook qGuestBook = QGuestBook.guestBook;
        String keyword = "1";
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression exTitle = qGuestBook.title.contains(keyword);
        BooleanExpression exContent = qGuestBook.content.contains(keyword);
        BooleanExpression exAll = exTitle.or(exContent);
        builder.and(exAll); // Expression 결합
        builder.and(qGuestBook.gNo.gt(0L)); // 이 값을 지닌 값 검색

        Page<GuestBook> result = guestBookRepository.findAll(builder, pageable);
        result.stream().forEach(guestBook -> {
            System.out.println(guestBook);
        });
    }
}
