package com.studyhub.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

// 스터디카페 예약 조회 응답용 DTO
@Getter
@AllArgsConstructor
public class OwnerReservationResponse {
    private String roomName;
    private int roomCapacity;
    private String userName;
    private String userPhone;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int price;
}