package com.project.joinus.repository;

import com.project.joinus.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationRepository extends JpaRepository<MemberEntity, Long> {

}