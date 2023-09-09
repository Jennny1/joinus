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
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long evalutionId;

}
