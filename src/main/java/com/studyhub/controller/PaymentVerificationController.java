package com.studyhub.controller;

import com.studyhub.domain.enums.PaymentType;
import com.studyhub.dto.PaymentHistoryResponse;
import com.studyhub.dto.PaymentVerificationRequest;
import com.studyhub.service.PaymentHistoryService;
import com.studyhub.service.PortOneService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentVerificationController {

    private final PortOneService portOneService;
    private final PaymentHistoryService paymentHistoryService;

    // 포트원 V2 포인트 충전 검증 API
    @PostMapping("/verify")
    public ResponseEntity<String> verifyPayment(@RequestBody PaymentVerificationRequest request) {
        boolean valid = portOneService.verifyPaymentByPaymentId(request.getPaymentId(), request.getAmount());
        if (!valid) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("결제 검증 실패");
        }

        paymentHistoryService.recordPointCharge(request.getUserId(), request.getAmount());
        return ResponseEntity.ok("결제 검증 및 포인트 충전 성공");
    }


    // 로그인한 사용자 본인의 결제 내역 조회 (세션 기반)
    @GetMapping("/history")
    public ResponseEntity<List<PaymentHistoryResponse>> getMyHistory(
            HttpSession session,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false) PaymentType type
    ) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (from == null) from = LocalDate.of(2000, 1, 1);
        if (to == null) to = LocalDate.now();

        List<PaymentHistoryResponse> result = (type != null)
                ? paymentHistoryService.getPaymentHistoryByType(userId, from, to, type)
                : paymentHistoryService.getPaymentHistory(userId, from, to);

        return ResponseEntity.ok(result);
    }

}
