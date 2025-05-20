package com.studyhub.service;

import com.studyhub.domain.StudyRoom;
import com.studyhub.repository.StudyRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyRoomServiceImpl implements StudyRoomService {

    private final StudyRoomRepository studyRoomRepository;

    @Override
    public StudyRoom registerAndReturn(StudyRoom room) {
        return studyRoomRepository.save(room);
    }

    @Override
    public List<StudyRoom> getRoomsByCafeId(Long studyCafeId) {
        return studyRoomRepository.findByStudyCafeId(studyCafeId);
    }
}
