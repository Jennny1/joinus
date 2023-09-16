package com.project.joinus.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberPasswordInput {

    @NotBlank(message = "기존 이메일을 입력해주세요")
    // 이메일
    private String email;


    @NotBlank(message = "기존 비밀번호를 입력해주세요")
    // 기존 비밀번호
    private String password;

    @NotBlank(message = "변경할 비밀번호를 입력해주세요")
    // 새 비밀번호
    private String newPassword;

}
