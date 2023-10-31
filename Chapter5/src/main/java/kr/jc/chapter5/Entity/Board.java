package kr.jc.chapter5.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer")
public class Board extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;       // PK
    private String title;   // 제목
    private String content; // 내용
    @ManyToOne
    private Member writer; // 연관관계 지정.
}
