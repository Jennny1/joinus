package com.project.joinus.repository;

import com.project.joinus.entity.MemberEntity;
import com.project.joinus.model.Member;
import com.project.joinus.model.MemberInput;
import com.project.joinus.model.MemberPasswordInput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
  Optional<Member> findByEmail(String email);

  MemberInput findByUserNameAndPassword(String userName, String password);

  boolean existsByEmail(String email);

  boolean existsByUserName(String userName);
}
