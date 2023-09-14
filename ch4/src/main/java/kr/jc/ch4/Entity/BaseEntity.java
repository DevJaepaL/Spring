package kr.jc.ch4.Entity;

/* 날짜 & 시간 처리를 위한 추상 클래스 */

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
abstract class BaseEntity {

    // DB에 들어갈 컬럼 이름 : regDate
    // 글 등록 일자
    @CreatedDate
    @Column(name = "regDate", updatable = false)
    private LocalDateTime regDate;

    // DB에 들어갈 컬럼 이름 : modDate
    // 수정 일자
    @LastModifiedDate
    @Column(name = "modDate")
    private LocalDateTime modDate;
}
