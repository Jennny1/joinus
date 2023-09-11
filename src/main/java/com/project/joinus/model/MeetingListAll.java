package com.project.joinus.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingListAll {

  // 모임 아이디
  private long meetingId;

  // 모임 제목
  private String title;

  // 모임 내용
  private String content;

  // 장소
  private String place;

  // 모임 날짜
  private LocalDateTime meetingDate;

  // 관심사
  private String favorit;

  // 참석인원
  private int attendees;

  // 취소여부
  private boolean isCalcled;

  // 완료여부
  private boolean isComplete;
}
