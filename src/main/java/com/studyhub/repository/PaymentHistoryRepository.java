package com.studyhub.repository;

import com.studyhub.domain.PaymentHistory;
import com.studyhub.domain.enums.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {
    /* 결제 내역 조회 */
    // ✅ 1. 사용자 ID와 날짜 범위로 조회
    List<PaymentHistory> findByUserIdAndPaidAtBetween(Long userId, LocalDateTime from, LocalDateTime to);

    // ✅ 2. 사용자 ID, 날짜 범위, 결제 타입(CHARGE / STUDY_ROOM)으로 조회
    List<PaymentHistory> findByUserIdAndTypeAndPaidAtBetween(Long userId, PaymentType type, LocalDateTime from, LocalDateTime to);
}
