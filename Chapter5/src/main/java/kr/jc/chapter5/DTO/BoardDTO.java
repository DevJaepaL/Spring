package kr.jc.chapter5.DTO;

import lombok.*;

import java.time.LocalDateTime;

/* DTO(Data Transfer Object)란 ?
*  ->   각 서비스 계층 간의 데이터 교환을 위해 사용하는 객체이다.
*       로직을 가지지 않고 순수히 데이터만을 갖고 있다. (Getter & Setter)
*       예를 들면 다음 예시를 확인한다.
*       1) 유저가 자신의 브라우저에 데이터를 입력하면 HTML 상에 있는 Form을 DTO에 담는다.
*       2) 해당 DTO를 받은 서버가 DAO(Data Access Object)를 이용해 전달받아 데이터베이스에 데이터를 담는다.  */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
    private Long bno;               // ID(PK)
    private String title;           // 제목
    private String content;         // 내용
    private String writerEmail;     // 작성자 이메일(PK)
    private String writerName;      // 작성자 이름
    private LocalDateTime regDate;  // 등록 날짜
    private LocalDateTime modDate;  // 수정 날짜
    private int replyCount;         // 글의 댓글 수
}
