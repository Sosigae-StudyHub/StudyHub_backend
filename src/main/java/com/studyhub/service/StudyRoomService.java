package com.studyhub.service;

import com.studyhub.domain.StudyRoom;
import java.util.List;

public interface StudyRoomService {
    List<StudyRoom> getRoomsByCafeId(Long studyCafeId);
    StudyRoom registerAndReturn(StudyRoom room);
}
