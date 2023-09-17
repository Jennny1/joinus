package com.project.joinus.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "meeting")
public class MeetingEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  // 모임 아이디
  long meetingId;

  @ManyToOne
  @JoinColumn
  // 회원아이디
  private MemberEntity member;

  // 글 제목
  @NotBlank(message = "모임 제목은 필수 입력 대상입니다.")
  private String title;

  // 글 내용
  @NotBlank(message = "모임 내용은 필수 입력 대상입니다.")
  private String content;

  // 장소
  @NotBlank(message = "모임 장소는 필수 입력 대상입니다.")
  private String place;

  // 모임 날짜
  private LocalDateTime meetingDate;

  // 모임 분류
  private String classification;

  // 참석인원
  private int attendees;

  // 모집인원
  private int recruitment;

  // 남은인원 (참석인원 - 모집인원)
  private int remain;

  // 글 등록일
  private LocalDateTime regDate;

  // 글 수정일
  private LocalDateTime updateDate;


  // 취소여부(펑여부)
  private boolean isCalcled;

  // 완료여부
  private boolean isComplete;


  public boolean isCalcled() {
    return isCalcled;
  }

  public void setCalcled(boolean calcled) {
    isCalcled = calcled;
  }

  public boolean isComplete() {
    return isComplete;
  }

  public void setComplete(boolean complete) {
    isComplete = complete;
  }
}
