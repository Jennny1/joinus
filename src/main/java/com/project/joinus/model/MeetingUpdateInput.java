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
public class MeetingUpdateInput {

  // 모임 제목
  private String title;

  // 모임 내용
  private String content;

  // 장소
  private String place;

  // 모임 날짜
  @NotBlank(message = "모임 날짜는 필수 입력 대상입니다.")
  private LocalDateTime meetingDate;

  // 모임 분류
  private String classification;

  // 참석인원
  private int attendees;

  // 모집인원
  private int recruitment;

  // 남은인원 (참석인원 - 모집인원)
  private int remain;

  // 취소여부
  private boolean isCalcled;

  // 완료여부
  private boolean isComplete;
}
