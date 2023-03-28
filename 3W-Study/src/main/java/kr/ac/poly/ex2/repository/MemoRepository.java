package kr.ac.poly.ex2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import kr.ac.poly.ex2.entity.Memo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Integer> {
    List<Memo> findByMnoBetweenOrderByMnoDesc(int from, int to);
    Page<Memo> findByMnoBetween(int from, int to, Pageable pageable);

    void deleteMemoByMnoLessThan(int delNum);

    /** Query를 사용하여 JPQL을 실행한다.
     *  JPQL을 사용해 SELECT 문 실행.
     *  mno 속성을 기준으로 내림차순 정렬하는 쿼리문
     */
    @Query("select m from Memo m order by m.mno desc")
    List<Memo> getListDesc();

    /** 파라미터 바인딩
     *  int mno와 String memoText가 가리키는 것은, 실제 DB 컬럼 "mno" & "memoText"이다.
     *  그 후 @Query 어노테이션으로 넘어가 UPDATE & WHERE 문 등 조건에 전달된다. */
    @Transactional
    @Modifying
    @Query("update Memo m set m.memoText = :memoText where m.mno = :mno")
    int updateMemoText(@Param("mno") int mno, @Param("memoText") String memoText);

    /** 파리미터 값이 많을 경우 ':' 대신에 ':#'를 이용하여 객체 전달이 가능하다.
     *  #param.memoText = Memo 테이블에 memoText 객체(속성) 참조
     *  #param.mno = Memo 테이블에 mno 객체(속성) 참조 */
    @Transactional
    @Modifying
    @Query("update Memo m set m.memoText = :#{#param.memoText} where m.mno = :#{#param.mno}")
    int updateMemoText2(@Param("param") Memo memo);

    /** Pageable 타입을 파라미터로 적용하여 쿼리문을 페이징 처리가 가능하다 */
    @Query(value = "select m from Memo m where m.mno > :mno",
            countQuery = "select count(m) from Memo m where m.mno > :mno")
    Page<Memo> getListWithQuery(int mno, Pageable pageable);
}
