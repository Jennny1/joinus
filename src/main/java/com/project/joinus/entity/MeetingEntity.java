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
  @NotBlank(message = "모임 날짜는 필수 입력 대상입니다.")
  private LocalDateTime meetingDate;

  // 모임 분류
  private String classification;

  // 참석인원
  @NotBlank(message = "모임 참석 인원은 필수 입력 대상입니다.")
  @Max(value = 10, message = "최대로 지정할 수 있는 인원은 10명입니다.")
  @Min(value = 3, message = "최소로 지정할 수 있는 인원은 10명입니다.")
  private int attendees;

  // 취소여부
  private boolean isCalcled;

  // 모임장여부
  private boolean isLeader;

  // 완료여부
  private boolean isComplete;

  // 글 등록일
  private LocalDateTime regDate;

  // 글 수정일
  private LocalDateTime updateDate;


  public boolean isCalcled() {
    return isCalcled;
  }

  public boolean isLeader() {
    return isLeader;
  }

  public boolean isComplete() {
    return isComplete;
  }
}
