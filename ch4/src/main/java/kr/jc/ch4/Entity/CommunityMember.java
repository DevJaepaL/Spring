package kr.jc.ch4.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class CommunityMember {

    // Primary Key(PK) => 유저의 Email
    @Id
    private String userEmail;

    private String password;

    private String userName;
}
