package com.studyhub.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 결제 검증 요청을 위한 DTO.
 * 프론트엔드에서 paymentId, userId, amount를 전달합니다.
 */
@Getter
@Setter
public class PaymentVerificationRequest {
    private String paymentId; // PortOne에서 전달받은 결제 ID
    private Long userId;      // 결제한 사용자 ID
    private int amount;       // 결제 금액
}