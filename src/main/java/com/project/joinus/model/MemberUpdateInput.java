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

    // 관심사
    @NotBlank(message = "수정할 관심사를 입력해주세요")
    private String favorit;

    private String email;

    // 수정일
    private LocalDateTime updateDate;



}
