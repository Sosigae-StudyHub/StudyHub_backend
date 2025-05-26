package com.studyhub.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

// ✅ 예약 상세 페이지에 필요한 필드를 담은 응답 DTO (가연)
@Getter
@AllArgsConstructor
public class ReservationDetailResponse {
    private String cafeName;
    private String roomName;
    private int roomCapacity;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String imageUrl; // room 이미지 경로 (없으면 null 또는 기본값)
    private String preReservationNotice;
    private String cancelNotice;
}
