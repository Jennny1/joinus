package com.project.joinus.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MemberInput {

  @NotBlank(message = "유저이름은 필수 입력 대상입니다.")
  // 유저이름
  private String userName;

  @NotBlank(message = "비밀번호는 필수 입력 대상입니다.")
  // 패스워드
  private String password;

  @Email(message = "이메일은 필수 입력 대상입니다.")
  // 이메일
  private String email;

  @NotBlank(message = "관심사1은 필수 입력 대상입니다.")
  // 관심사1
  private String favorit1;

  // 관심사2
  private String favorit2;

  // 포인트
  private long point;

  // 등록일
  private LocalDateTime regDate;

  // 수정일
  private LocalDateTime updateDate;

}
