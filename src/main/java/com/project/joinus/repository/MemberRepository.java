package com.project.joinus.repository;

import com.project.joinus.entity.MemberEntity;
import com.project.joinus.model.MemberInput;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
  Optional<MemberEntity> findByEmail(String email);

  boolean existsByEmail(String email);

  boolean existsByUserName(String userName);
}
