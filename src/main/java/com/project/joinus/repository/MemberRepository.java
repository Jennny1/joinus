package com.project.joinus.repository;

import com.project.joinus.entity.MemberEntity;
import com.project.joinus.model.MemberInput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberInput> findByEmail(String email);
}
