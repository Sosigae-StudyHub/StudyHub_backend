package com.studyhub.controller;

import com.studyhub.dto.ReservationRequest;
import com.studyhub.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // ✅ 예약 생성
    @PostMapping("/make")
    public ResponseEntity<?> makeReservation(@RequestBody ReservationRequest request) {
        try {
            reservationService.makeReservation(request);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage()); // ✅ 포인트 부족 or 예약 충돌
        }
    }

    // ✅ 예약 중복 확인
    @PostMapping("/check-overlap")
    public ResponseEntity<Boolean> checkOverlap(@RequestBody ReservationRequest request) {
        boolean isOverlap = reservationService.isOverlappingReservation(
                request.getStudyRoomId(),
                request.getStartTime(),
                request.getEndTime()
        );
        return ResponseEntity.ok(isOverlap);
    }


}
