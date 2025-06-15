package com.studyhub.repository;

import com.studyhub.domain.AvailableTimeBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvailableTimeBlockRepository extends JpaRepository<AvailableTimeBlock, Long> {

    List<AvailableTimeBlock> findByStudyRoomIdAndIsAvailable(Long studyRoomId, boolean isAvailable);
    void deleteByStudyRoomId(Long studyRoomId);

}
