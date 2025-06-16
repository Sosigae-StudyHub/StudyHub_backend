package com.studyhub.service;

import com.studyhub.dto.OwnerReservationResponse;
import com.studyhub.dto.ReservationDetailResponse;
import com.studyhub.dto.ReservationRequest;
import com.studyhub.dto.ReservationSummaryResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ReservationService {
    // ✅ 예약 생성(지우)
    void makeReservation(ReservationRequest request);
    //// ✅ 예약 겹침 여부만 확인(지우)
    boolean isOverlappingReservation(Long roomId, LocalDateTime start, LocalDateTime end);

    // ✅ 특정 사용자(userId)의 날짜별 예약 리스트 반환(가연) - 사용자 캘린더 용
    List<ReservationSummaryResponse> getReservationsByDate(Long userId, LocalDate date);

    // ✅ 예약 상세 정보 반환 메서드(가연)
    ReservationDetailResponse getReservationDetails(Long reservationId, Long userId);

    // ✅ 마이페이지에서 현재 예약 내역 보여주기 (가연)
    Map<String, Object> getCurrentReservationDetails(Long userId);

    // ✅ 사업자 캘린더 - 현재 시간 기준 앞으로의 예약 내역만 가져옴 (가연)
    List<OwnerReservationResponse> getFutureReservationsForOwner(Long cafeId); // 스터디카페 별 예약 내역 불러오기

}
