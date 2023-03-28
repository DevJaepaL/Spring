package kr.ac.poly.ex2.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_memo")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Memo {
    
    // 어노테이션으로 PK 지정
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mno;

    // 속성 생성
    @Column(length = 200, nullable = false)
    private String memoText;
}
