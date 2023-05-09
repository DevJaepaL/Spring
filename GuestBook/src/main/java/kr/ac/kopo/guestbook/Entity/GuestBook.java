package kr.ac.kopo.guestbook.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuestBook extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gNo; // Guest Number

    @Column(length = 100, nullable = false)
    private String title; // 제목

    @Column(length = 1500, nullable = false)
    private String content; // 내용

    @Column(length = 50, nullable = false)
    private String writer; // 글쓴이

    /** 제목 수정 Method */
    public void changeTitle(String edit_TitleName) {
        this.title = edit_TitleName;
    }

    /** 내용 수정 Method */
    public void changeContent(String edit_content) {
        this.content = edit_content;
    }
}
