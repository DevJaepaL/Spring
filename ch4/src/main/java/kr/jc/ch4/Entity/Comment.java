package kr.jc.ch4.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "userBoard")
public class Comment extends BaseEntity{

    /* PK & 데이터 생성 시, 자동 생성 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentNo;
    private String comment;
    // 답장자;
    private String replyer;
    // N:1 관계
    @ManyToOne
    private UserBoard userBoard;
}
