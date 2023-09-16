package com.project.joinus.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 회원아이디
    private long memberId;

    @Column(nullable = false)
    // 유저네임
    private String userName;

    @Column(nullable = false)
    // 패스워드
    private String password;

    @Column(nullable = false)
    // 이메일
    private String email;

    @Column(nullable = false)
    // 관심사
    private String favorit;

    @Column(nullable = false)
    // 탈퇴여부
    private boolean isQuit;

    @Column(nullable = false)
    // 포인트
    private long point;

    // 등록일
    private LocalDateTime regDate;

    @Column
    // 수정일
    private LocalDateTime updateDate;


    public boolean isQuit() {
        return isQuit;
    }
}
