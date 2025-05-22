package com.studyhub.controller;

import com.studyhub.dto.PaymentVerificationRequest;
import com.studyhub.service.PaymentHistoryService;
import com.studyhub.service.PortOneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentVerificationController {

    private final PortOneService portOneService;
    private final PaymentHistoryService paymentHistoryService;

    @PostMapping("/verify")
    public ResponseEntity<String> verifyPayment(@RequestBody PaymentVerificationRequest request) {
        boolean valid = portOneService.verifyPaymentByPaymentId(request.getPaymentId(), request.getAmount());
        if (!valid) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("결제 검증 실패");
        }

        paymentHistoryService.recordPointCharge(request.getUserId(), request.getAmount());
        return ResponseEntity.ok("결제 검증 및 포인트 충전 성공");
    }
}
