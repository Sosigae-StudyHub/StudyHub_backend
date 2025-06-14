package com.studyhub.service.impl;

import com.studyhub.domain.PaymentHistory;
import com.studyhub.domain.Reservation;
import com.studyhub.domain.User;
import com.studyhub.domain.enums.PaymentType;
import com.studyhub.dto.PaymentHistoryResponse;
import com.studyhub.repository.PaymentHistoryRepository;
import com.studyhub.repository.UserRepository;
import com.studyhub.service.PaymentHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentHistoryServiceImpl implements PaymentHistoryService {

    private final PaymentHistoryRepository paymentHistoryRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void recordPointCharge(Long userId, int amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        user.setPoint(user.getPoint() + amount);

        PaymentHistory history = PaymentHistory.builder()
                .user(user)
                .amount(amount)
                .type(PaymentType.CHARGE)
                .paidAt(LocalDateTime.now())
                .build();

        paymentHistoryRepository.save(history);
    }

    // 예약 결제 처리 (포인트 차감 + 결제 내역 저장)
    @Override
    @Transactional
    public void recordStudyRoomPayment(User user, Reservation reservation) {
        int price = reservation.getStudyRoom().getPrice();

        if (user.getPoint() < price) {
            throw new IllegalStateException("포인트가 부족합니다.");
        }

        user.setPoint(user.getPoint() - price); // 포인트 차감

        PaymentHistory history = PaymentHistory.builder()
                .user(user)
                .amount(price)
                .type(PaymentType.STUDY_ROOM)
                .paidAt(LocalDateTime.now())
                .reservation(reservation)
                .build();

        paymentHistoryRepository.save(history);
    }

    /* 결제 내역 조회 */
    // 1. 전체 조회 (유형 X)
    @Override
    public List<PaymentHistoryResponse> getPaymentHistory(Long userId, LocalDate from, LocalDate to) {
        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(23, 59, 59);

        return paymentHistoryRepository.findByUserIdAndPaidAtBetween(userId, start, end).stream()
                .map(PaymentHistoryResponse::new)
                .toList();
    }

    // 2. 유형 필터 조회
    @Override
    public List<PaymentHistoryResponse> getPaymentHistoryByType(Long userId, LocalDate from, LocalDate to, PaymentType type) {
        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(23, 59, 59);

        return paymentHistoryRepository.findByUserIdAndTypeAndPaidAtBetween(userId, type, start, end).stream()
                .map(PaymentHistoryResponse::new)
                .toList();
    }
}

