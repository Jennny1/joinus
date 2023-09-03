package com.project.joinus.entity;

import java.awt.geom.GeneralPath;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
  String memberId;

  @Column(nullable = false)
  // 닉네임(유저네임)
  String userName;

  @Column(nullable = false)
  // 패스워드
  String password;

  // 관심사1
  String favorit1;

  // 관심사2
  String favorit2;

  @Column(nullable = false)
  // 탈퇴여부
  boolean isQuit;

  @Column(nullable = false)
  // 포인트
  long point;

  @Column(nullable = false)
  // 등록일
  LocalDateTime regDate;

}
