package com.project.joinus.model;

import com.project.joinus.entity.MeetingEntity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReserveInput {

  // 모임id
  private long meetingId;

  // 종료여부
  private boolean isComplete;

}
