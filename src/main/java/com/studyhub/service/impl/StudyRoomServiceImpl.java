package com.studyhub.service.impl;

import com.studyhub.domain.AvailableTimeBlock;
import com.studyhub.domain.StudyRoom;
import com.studyhub.dto.StudyRoomResponse;
import com.studyhub.repository.AvailableTimeBlockRepository;
import com.studyhub.repository.StudyRoomRepository;
import com.studyhub.service.StudyRoomService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyRoomServiceImpl implements StudyRoomService {

    private final StudyRoomRepository studyRoomRepository;
    private final AvailableTimeBlockRepository availableTimeBlockRepository;

    @Override
    public StudyRoom registerAndReturn(StudyRoom room) {
        return studyRoomRepository.save(room);
    }

    @Override
    public List<StudyRoom> getRoomsByCafeId(Long studyCafeId) {
        return studyRoomRepository.findByStudyCafeId(studyCafeId);
    }

    @Override
    @Transactional
    public StudyRoom updateRoomWithTimeBlocks(StudyRoom room, List<Integer> availableHours) {
        StudyRoom existing = studyRoomRepository.findById(room.getId())
                .orElseThrow(() -> new EntityNotFoundException("스터디룸이 존재하지 않습니다."));

        existing.setName(room.getName());
        existing.setMaxCapacity(room.getMaxCapacity());
        existing.setPrice(room.getPrice());
        existing.setEquipmentInfo(room.getEquipmentInfo());
        existing.setPreReservationNotice(room.getPreReservationNotice());
        existing.setPostReservationNotice(room.getPostReservationNotice());
        existing.setCancelNotice(room.getCancelNotice());
        existing.setDescription(room.getDescription());

        // 기존 시간 삭제
        availableTimeBlockRepository.deleteByStudyRoomId(existing.getId());

        // 새로 저장
        List<AvailableTimeBlock> timeBlocks = new ArrayList<>();
        for (int h = 0; h < 24; h++) {
            timeBlocks.add(AvailableTimeBlock.builder()
                    .studyRoom(existing)
                    .hour(h)
                    .isAvailable(availableHours.contains(h))
                    .dayOfWeek("MONDAY")
                    .build());
        }
        availableTimeBlockRepository.saveAll(timeBlocks);

        return studyRoomRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteRoomWithValidation(Long roomId, Long ownerId) {
        StudyRoom room = studyRoomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("스터디룸을 찾을 수 없습니다."));

        if (!room.getStudyCafe().getOwner().getId().equals(ownerId)) {
            throw new AccessDeniedException("해당 스터디룸의 소유자가 아닙니다.");
        }

        // 예약 시간 블록 먼저 삭제
        availableTimeBlockRepository.deleteByStudyRoomId(roomId);

        // 스터디룸 삭제
        studyRoomRepository.delete(room);
    }

    @Override
    public List<StudyRoomResponse> getRoomsResponseByCafeId(Long cafeId) {
        List<StudyRoom> rooms = studyRoomRepository.findByStudyCafeId(cafeId);
        return rooms.stream()
                .map(StudyRoomResponse::new)
                .toList();
    }

    @Override
    public List<StudyRoom> getRoomsByCafeId(Long studyCafeId) {
        return studyRoomRepository.findByStudyCafeId(studyCafeId);
    }

}
