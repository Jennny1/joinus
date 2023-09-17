package com.project.joinus.model;

import com.project.joinus.entity.MemberEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingCreateInput {

  // 모임 아이디
  private long meetingId;

  // 멤버 아이디
  private MemberEntity member;

  // 모임 제목
  private String title;

  // 모임 내용
  private String content;

  // 장소
  private String place;

  // 모임 날짜
  private String meetingDate;

  // 참석인원
  private int attendees;

  // 모임 분류
  private String classification;

  // 취소여부
  private boolean isCalcled;

  // 완료여부
  private boolean isComplete;

  // 글 등록일
  private LocalDateTime regDate;

  // 글 수정일
  private LocalDateTime updateDate;


}
