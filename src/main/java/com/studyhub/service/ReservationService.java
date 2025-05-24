package com.studyhub.service;

import com.studyhub.dto.ReservationRequest;

import java.time.LocalDateTime;

public interface ReservationService {

    void makeReservation(ReservationRequest request);

    boolean isOverlappingReservation(Long roomId, LocalDateTime start, LocalDateTime end);
}
