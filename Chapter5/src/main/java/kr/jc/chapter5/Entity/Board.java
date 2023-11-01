package kr.jc.chapter5.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer")       // ToString -> 무조건 exclude
public class Board extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;       // PK(ID)
    private String title;   // 제목
    private String content; // 내용
    @ManyToOne (fetch = FetchType.LAZY) // FK 연관 관계 지정 -> 하나의 유저는 여러 개의 게시글을 가질 수 있다. (O)
    private Member writer;
}
