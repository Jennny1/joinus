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
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long reserveId;



}
