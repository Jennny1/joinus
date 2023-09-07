package com.project.joinus.domain;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MemberInputDto {

  @NotBlank(message = "유저이름은 필수 입력 대상입니다.")
  // 유저이름
  String userName;

  @NotBlank(message = "비밀번호는 필수 입력 대상입니다.")
  // 패스워드
  String password;

  @Email(message = "이메일은 필수 입력 대상입니다.")
  // 이메일
  String email;

  @NotBlank(message = "관심사1은 필수 입력 대상입니다.")
  // 관심사1
  String favorit1;

  // 관심사2
  String favorit2;

  // 포인트
  long point;

  // 등록일
  LocalDate regDate;

}
