package com.studyhub.service;

import com.studyhub.dto.ReservationRequest;

import java.time.LocalDateTime;

public interface ReservationService {
    void makeReservation(ReservationRequest request); // 예약 생성
    boolean isOverlappingReservation(Long roomId, LocalDateTime start, LocalDateTime end); // 겹침 확인
}