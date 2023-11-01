package kr.jc.chapter5.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "board")
public class Reply extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;       // PK(Reply Number)
    private String text;    // 내용
    private String replyer; // 답장자

    @ManyToOne  // FK 연관관계 지정 -> 게시글 하나에 댓글 여러개가 있을 수 있다.
    private Board board;
}
