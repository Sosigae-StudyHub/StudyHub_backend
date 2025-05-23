package com.studyhub.service;

import com.studyhub.domain.StudyRoom;
import com.studyhub.dto.StudyRoomResponse;
import com.studyhub.repository.StudyRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyRoomServiceImpl implements StudyRoomService {

    private final StudyRoomRepository studyRoomRepository;

    @Override
    public List<StudyRoomResponse> getRoomsByCafeId(Long cafeId) {
        List<StudyRoom> rooms = studyRoomRepository.findByStudyCafeId(cafeId);
        return rooms.stream()
                .map(StudyRoomResponse::new)
                .toList();
    }
}
