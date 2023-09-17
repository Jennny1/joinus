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

    // 유저네임
    @Column(nullable = false)
    private String userName;

    // 패스워드
    @Column(nullable = false)
    private String password;

    // 이메일
    @Column(nullable = false)
    private String email;

    // 관심사
    @Column(nullable = false)
    private String favorit;

    // 탈퇴여부
    @Column(nullable = false)
    private boolean isQuit;

    // 포인트
    @Column(nullable = false)
    private long point;

    // 등록일
    @Column
    private LocalDateTime regDate;


    // 모임장여부
    @Column
    private boolean isLeader;

    // 수정일
    @Column
    private LocalDateTime updateDate;


    public boolean isQuit() {
        return isQuit;
    }

    public boolean isLeader() {
        return isLeader;
    }

    public void setQuit(boolean quit) {
        isQuit = quit;
    }

    public void setLeader(boolean leader) {
        isLeader = leader;
    }
}
