package com.project.joinus.model;

import com.project.joinus.entity.MeetingEntity;
import com.project.joinus.entity.MemberEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Reserve {

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
