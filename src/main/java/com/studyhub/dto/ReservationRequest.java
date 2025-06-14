package com.studyhub.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationRequest {
    private Long userId;
    private Long studyRoomId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
