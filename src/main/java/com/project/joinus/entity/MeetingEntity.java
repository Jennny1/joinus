package com.project.joinus.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

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

  // 취소여부
  private boolean isCalcled;

  // 완료여부
  private boolean isComplete;

  // 글 등록일
  private LocalDateTime regDate;

  // 글 수정일
  private LocalDateTime updateDate;


  public boolean isCalcled() {
    return isCalcled;
  }

  public boolean isComplete() {
    return isComplete;
  }
}
