package com.studyhub.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// ✅ 예약 상세 페이지에 필요한 필드를 담은 응답 DTO (가연)
@Getter
@Setter
@AllArgsConstructor
public class ReservationDetailResponse {
    private String cafeName;
    private String cafeAddress;
    private String roomName;
    private int roomCapacity;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String postReservationNotice; // 예약자 공지사항
    private String cancelNotice;
}
