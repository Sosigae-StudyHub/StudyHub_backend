package com.studyhub.repository;

import com.studyhub.domain.AvailableTimeBlock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvailableTimeBlockRepository extends JpaRepository<AvailableTimeBlock, Long> {
    List<AvailableTimeBlock> findByStudyRoomId(Long studyRoomId);
    void deleteByStudyRoomId(Long studyRoomId);
}
