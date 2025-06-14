package com.studyhub.dto;

import com.studyhub.domain.PaymentHistory;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class PaymentHistoryResponse {

    private String date;           // yyyy-MM-dd 형식으로 반환
    private int amount;
    private String type;           // CHARGE / STUDY_ROOM
    private Long reservationId;    // STUDY_ROOM일 경우 포함, CHARGE일 경우 null

    // ✅ Entity → DTO 변환 생성자
    public PaymentHistoryResponse(PaymentHistory entity) {
        this.date = entity.getPaidAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.amount = entity.getAmount();
        this.type = entity.getType().name();
        this.reservationId = entity.getReservation() != null ? entity.getReservation().getId() : null;
    }
}
