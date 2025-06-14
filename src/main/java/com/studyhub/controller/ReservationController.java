package com.studyhub.controller;

import com.studyhub.dto.ReservationDetailResponse;
import com.studyhub.dto.ReservationRequest;
import com.studyhub.dto.ReservationSummaryResponse;
import com.studyhub.service.ReservationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // ✅ 예약 생성(지우)
    @PostMapping("/make")
    public ResponseEntity<?> makeReservation(@RequestBody ReservationRequest request) {
        try {
            reservationService.makeReservation(request);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage()); // ✅ 포인트 부족 or 예약 충돌(가연 코드 추가)
        }
    }

    // ✅ 예약 중복 확인 (지우)
    @PostMapping("/check-overlap")
    public ResponseEntity<Boolean> checkOverlap(@RequestBody ReservationRequest request) {
        boolean isOverlap = reservationService.isOverlappingReservation(
                request.getStudyRoomId(),
                request.getStartTime(),
                request.getEndTime()
        );
        return ResponseEntity.ok(isOverlap);
    }

    // ✅ 로그인한 사용자의 특정 날짜 예약 요약 리스트 조회 API (가연) - 사용자 캘린더용
    @GetMapping("/by-date")
    public ResponseEntity<?> getReservationsByDate(
            HttpSession session,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        List<ReservationSummaryResponse> result = reservationService.getReservationsByDate(userId, date);
        return ResponseEntity.ok(result);
    }

    // ✅ 예약 ID로 상세 정보 조회 API
    @GetMapping("/{reservationId}/details")
    public ResponseEntity<?> getReservationDetails(
            @PathVariable Long reservationId,
            HttpSession session
    ) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        try {
            ReservationDetailResponse response = reservationService.getReservationDetails(reservationId, userId);
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentReservation(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(401).body("로그인이 필요합니다.");

        Map<String, Object> result = reservationService.getCurrentReservationDetails(userId);
        if (result == null) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(result);
    }

}
