package com.project.joinus.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {
  // 회원아이디
  long memberID;

  // 닉네임
  String nickname;

  // 패스워드
  String password;

  // 관심사1
  String favorit1;

  // 관심사2
  String favorit2;

  // 탈퇴여부
  boolean isQuit;
  // 포인트
  long point;

  // 등록일
  LocalDate regDate;


}
