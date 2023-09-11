package com.project.joinus.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberUpdateInput {


    @Email(message = "이메일은 필수 입력 대상입니다.")
    // 이메일
    private String email;

    // 유저이름
    private String userName;

    // 관심사
    private String favorit;

    // 수정일
    private LocalDateTime updateDate;



}
