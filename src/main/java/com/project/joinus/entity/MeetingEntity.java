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
  long meetingId;
}
