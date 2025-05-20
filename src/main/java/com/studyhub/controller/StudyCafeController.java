package com.studyhub.controller;

import com.studyhub.domain.StudyCafe;
import com.studyhub.domain.User;
import com.studyhub.dto.StudyCafeRequest;
import com.studyhub.dto.StudyCafeSimpleDto;
import com.studyhub.service.StudyCafeService;
import com.studyhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/cafes")
public class StudyCafeController {

    private final StudyCafeService studyCafeService;
    private final UserService userService;

    @Autowired
    public StudyCafeController(StudyCafeService studyCafeService, UserService userService) {
        this.studyCafeService = studyCafeService;
        this.userService = userService;
    }

    // 스터디카페 전체 조회
    @GetMapping("/all")
    public List<StudyCafeSimpleDto> getAllCafes() {
        List<StudyCafe> cafes = studyCafeService.getAllCafes();
        return cafes.stream()
                .map(c -> new StudyCafeSimpleDto(
                        c.getId(),
                        c.getName(),
                        c.getAddress(),
                        c.getLatitude(),
                        c.getLongitude()
                ))
                .toList();
    }

    // (사업자) 스터디카페 등록
    @PostMapping("/register")
    public ResponseEntity<StudyCafe> registerCafe(@RequestBody StudyCafeRequest request) {
        User owner = userService.findById(request.getOwnerId());

        StudyCafe cafe = StudyCafe.builder()
                .name(request.getName())
                .address(request.getAddress())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .contact(request.getContact())
                .businessHour(request.getBusinessHour())
                .owner(owner)
                .notice(request.getNotice())
                .reservationCheckMessage(request.getReservationCheckMessage())
                .build();

        System.out.println("등록된 스터디카페 정보: " + cafe);

        studyCafeService.registerCafe(cafe);
        return ResponseEntity.ok(cafe);
    }

    // (사업자) 자신이 등록한 스터디카페 목록 조회
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<StudyCafeSimpleDto>> getCafesByOwner(@PathVariable Long ownerId) {
        List<StudyCafe> cafes = studyCafeService.getCafesByOwner(ownerId);

        List<StudyCafeSimpleDto> dtoList = cafes.stream()
                .map(c -> new StudyCafeSimpleDto(
                        c.getId(),
                        c.getName(),
                        c.getAddress(),
                        c.getLatitude(),
                        c.getLongitude()
                ))
                .toList();

        return ResponseEntity.ok(dtoList);
    }

    // (사업자) 스터디카페 수정
    @PutMapping("/update")
    public ResponseEntity<StudyCafe> updateCafe(@RequestBody StudyCafeRequest request) {
        User owner = userService.findById(request.getOwnerId());

        StudyCafe cafe = StudyCafe.builder()
                .id(request.getId())
                .name(request.getName())
                .address(request.getAddress())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .contact(request.getContact())
                .businessHour(request.getBusinessHour())
                .owner(owner)
                .notice(request.getNotice())
                .reservationCheckMessage(request.getReservationCheckMessage())
                .build();

        StudyCafe updated = studyCafeService.updateCafe(cafe);
        return ResponseEntity.ok(updated);
    }

    // (사업자) 스터디카페 삭제
    @DeleteMapping("/delete/{cafeId}")
    public ResponseEntity<Void> deleteCafe(@PathVariable Long cafeId, @RequestParam Long ownerId) {
        studyCafeService.deleteCafe(cafeId, ownerId);
        return ResponseEntity.ok().build();
    }

    // (전체) 스터디카페 상세 조회
    @GetMapping("/{cafeId}")
    public ResponseEntity<StudyCafe> getCafeById(@PathVariable Long cafeId) {
        StudyCafe cafe = studyCafeService.getCafeById(cafeId);
        return ResponseEntity.ok(cafe);
    }

}

