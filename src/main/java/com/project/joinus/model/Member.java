package com.project.joinus.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Member {

  // 유저이름
  private String userName;

  // 패스워드
  private String password;

  // 이메일
  private String email;

  // 관심사
  private String favorit;

  // 모임장여부
  private boolean isLeader;

  // 포인트
  private long point;

  // 등록일
  private LocalDateTime regDate;

  // 수정일
  private LocalDateTime updateDate;

}
