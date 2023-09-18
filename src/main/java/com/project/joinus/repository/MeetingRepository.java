package com.project.joinus.repository;

import com.project.joinus.entity.MeetingEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends JpaRepository<MeetingEntity, Long> {

    Page<MeetingEntity> findAllByClassification(String classification, PageRequest regDate);

  Optional<Object> findByMeetingId(long id);
}
