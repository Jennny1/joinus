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
@Table(name = "evaluation")
public class EvaluationEntity {

  // 평가 아이디
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long evalutionId;

  // 회원
  @OneToOne
  @JoinColumn
  private MemberEntity member;

  // 모임
  @OneToOne
  @JoinColumn
  private MeetingEntity meeting;


}
