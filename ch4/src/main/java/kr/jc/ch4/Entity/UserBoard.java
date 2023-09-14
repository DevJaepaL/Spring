package kr.jc.ch4.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer")
public class UserBoard extends BaseEntity {
    /* PK & 자동 생성 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardNo;
    // 제목
    private String boardTitle;
    // 내용
    private String content;
    // 연관관계가 N:1의 관계임을 알린다.
    @ManyToOne
    private CommunityMember writer;
}
