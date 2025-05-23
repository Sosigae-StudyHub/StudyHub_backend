package com.studyhub.service;

import com.studyhub.dto.StudyRoomResponse;

import java.util.List;

public interface StudyRoomService {
    List<StudyRoomResponse> getRoomsByCafeId(Long cafeId);
}
