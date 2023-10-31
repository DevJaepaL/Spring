package kr.jc.chapter5.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "board")
public class Reply extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /* PK, 해당 값의 경우는 생성 시 자동으로 값 생성
    * ID값을 Null로 할 경우 DB에서 자동적으로 AUTO INCREMENT를 해준다. */
    private Long rno;
    private String text;    // 댓글 내용
    private String replyer; // 답장자
    @ManyToOne
    private Board board; // 연관관계 지정
}
