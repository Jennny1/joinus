package com.project.joinus.model;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Meeting {

  // 모임 아이디
  private long meetingId;

  // 모임 제목
  @NotBlank(message = "모임 제목은 필수 입력 대상입니다.")
  private String title;

  // 모임 내용
  @NotBlank(message = "모임 내용은 필수 입력 대상입니다.")
  private String content;

  // 장소
  @NotBlank(message = "모임 장소는 필수 입력 대상입니다.")
  private String place;

  // 모임 날짜
  @NotBlank(message = "모임 날짜는 필수 입력 대상입니다.")
  private LocalDateTime meetingDate;

  // 모임 분류
  private String classification;

  // 참석인원
  private int attendees;

  // 취소여부
  private boolean isCalcled;

  // 완료여부
  private boolean isComplete;
}
