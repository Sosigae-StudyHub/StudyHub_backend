package com.studyhub.controller;

import com.studyhub.domain.AvailableTimeBlock;
import com.studyhub.repository.AvailableTimeBlockRepository;
import com.studyhub.service.AvailableTimeBlockService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class AvailableTimeBlockController {
    private final AvailableTimeBlockService availableTimeBlockService; // ✅ 이 필드 선언 반드시 필요!
    //private final AvailableTimeBlockRepository availableTimeBlockRepository;

    @GetMapping("/unavailable")
    public ResponseEntity<List<Integer>> getUnavailableHours(@RequestParam Long roomId) {
        List<Integer> unavailableHours = availableTimeBlockService.getUnavailableHours(roomId); // ✅ 서비스 통해 호출
        return ResponseEntity.ok(unavailableHours);
    }

//    @GetMapping("/unavailable")
//    public ResponseEntity<List<Integer>> getUnavailableHours(@RequestParam Long roomId) {
//        List<Integer> unavailableHours = availableTimeBlockRepository
//                .findByStudyRoomIdAndIsAvailable(roomId, true)
//                .stream()
//                .map(AvailableTimeBlock::getHour)
//                .toList();
//
//        return ResponseEntity.ok(unavailableHours);
//    }

    @GetMapping("/unavailable/combined")
    public ResponseEntity<List<Integer>> getCombinedUnavailableHours(
            @RequestParam Long roomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<Integer> result = availableTimeBlockService.getFullyUnavailableHours(roomId, date);
        return ResponseEntity.ok(result);
    }

}
