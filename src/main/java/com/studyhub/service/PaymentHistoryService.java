package com.studyhub.service;

import com.studyhub.domain.Reservation;
import com.studyhub.domain.User;

public interface PaymentHistoryService {
    // 기존 충전 로직
    void recordPointCharge(Long userId, int amount);

    // ✅ 추가: 스터디룸 예약 결제 내역 저장
    void recordStudyRoomPayment(User user, Reservation reservation);
}
