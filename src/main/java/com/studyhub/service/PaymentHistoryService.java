package com.studyhub.service;

import com.studyhub.domain.Reservation;
import com.studyhub.domain.User;
import com.studyhub.domain.enums.PaymentType;
import com.studyhub.dto.PaymentHistoryResponse;

import java.time.LocalDate;
import java.util.List;

public interface PaymentHistoryService {
    // 기존 충전 로직
    void recordPointCharge(Long userId, int amount);

    /* 스터디룸 예약 결제 내역 저장 */
    void recordStudyRoomPayment(User user, Reservation reservation);

    /* 결제 내역 조회 */
    // ✅ 1. 결제 내역 전체 조회 (기간 필터)
    List<PaymentHistoryResponse> getPaymentHistory(Long userId, LocalDate from, LocalDate to);

    // ✅ 2. 결제 내역 유형 필터 조회 (CHARGE / STUDY_ROOM)
    List<PaymentHistoryResponse> getPaymentHistoryByType(Long userId, LocalDate from, LocalDate to, PaymentType type);
}
