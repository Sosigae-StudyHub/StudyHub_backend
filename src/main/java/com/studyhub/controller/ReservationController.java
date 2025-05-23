package com.studyhub.controller;

import com.studyhub.service.ReservationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentReservation(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(401).body("로그인이 필요합니다.");

        Map<String, Object> result = reservationService.getCurrentReservationDetails(userId);
        if (result == null) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(result);
    }
}
