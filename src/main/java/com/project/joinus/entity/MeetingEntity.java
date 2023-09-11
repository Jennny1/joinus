package com.project.joinus.entity;

import lombok.*;

import javax.persistence.*;
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
  private String title;

  // 글 내용
  private String content;

  // 장소
  private String place;

  // 모임 날짜
  private LocalDateTime meetingDate;



  // 참석인원
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



}
