package kr.jc.chapter5.Entity;

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
    // DB 컬럼 : 생성 날짜 , 값 변경(update) : false
    @CreatedDate
    @Column(name = "regDate", updatable = false)
    private LocalDateTime regDate;

    // DB 컬럼 : 변경 날짜
    @LastModifiedDate
    @Column(name = "modDate")
    private LocalDateTime modDate;
}
