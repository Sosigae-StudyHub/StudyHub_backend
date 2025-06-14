package com.studyhub.service;

import com.studyhub.domain.StudyRoom;
import com.studyhub.dto.StudyRoomResponse;

import java.util.List;

public interface StudyRoomService {
    List<StudyRoom> getRoomsByCafeId(Long studyCafeId);
    StudyRoom registerAndReturn(StudyRoom room);
    StudyRoom updateRoomWithTimeBlocks(StudyRoom room, List<Integer> availableHours);
    void deleteRoomWithValidation(Long roomId, Long ownerId);

    List<StudyRoomResponse> getRoomsResponseByCafeId(Long cafeId);

}
