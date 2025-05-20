package com.studyhub.repository;

import com.studyhub.domain.StudyRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyRoomRepository extends JpaRepository<StudyRoom, Long> {
    List<StudyRoom> findByStudyCafeId(Long studyCafeId);
}
