package com.project.joinus.repository;

import com.project.joinus.entity.ReserveEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReserveRepository extends JpaRepository <ReserveEntity, Long> {
}
