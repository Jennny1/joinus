package com.project.joinus.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reserve")
public class ReserveEntity {

  // 예약 아이디
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long reserveId;

  // 회원
  @OneToOne
  @JoinColumn
  private MemberEntity member;
  
  // 모임
  @OneToOne
  @JoinColumn
  private MeetingEntity meeting;

  // 종료여부
  private boolean isComplete;


}
