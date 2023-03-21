package kr.ac.poly.ex2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import kr.ac.poly.ex2.entity.Memo;
public interface MemoRepository extends JpaRepository<Memo, Integer> {
}
