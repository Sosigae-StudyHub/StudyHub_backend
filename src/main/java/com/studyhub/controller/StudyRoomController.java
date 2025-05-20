package com.studyhub.controller;

import com.studyhub.domain.AvailableTimeBlock;
import com.studyhub.domain.StudyCafe;
import com.studyhub.domain.StudyRoom;
import com.studyhub.dto.StudyRoomRequest;
import com.studyhub.repository.AvailableTimeBlockRepository;
import com.studyhub.service.StudyCafeService;
import com.studyhub.service.StudyRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class StudyRoomController {

    private final StudyRoomService studyRoomService;
    private final StudyCafeService studyCafeService;
    private final AvailableTimeBlockRepository availableTimeBlockRepository;


    // 스터디룸 등록
    @PostMapping("/register")
    public ResponseEntity<StudyRoom> registerRoom(@RequestBody StudyRoomRequest request) {
        StudyCafe cafe = studyCafeService.getCafeById(request.getStudyCafeId());

        System.out.println("카페 소유자 ID: " + cafe.getOwner().getId());
        System.out.println("요청한 ownerId: " + request.getOwnerId());

        if (!cafe.getOwner().getId().equals(request.getOwnerId())) {
            throw new AccessDeniedException("해당 스터디카페의 소유자가 아닙니다.");
        }

        // 스터디룸 생성
        StudyRoom room = StudyRoom.builder()
                .name(request.getName())
                .maxCapacity(request.getMaxCapacity())
                .price(request.getPrice())
                .equipmentInfo(request.getEquipmentInfo())
                .studyCafe(cafe)
                .preReservationNotice(request.getPreReservationNotice())
                .postReservationNotice(request.getPostReservationNotice())
                .cancelNotice(request.getCancelNotice())
                .build();

        StudyRoom savedRoom = studyRoomService.registerAndReturn(room);

        // 시간대 설정
        List<AvailableTimeBlock> timeBlocks = new ArrayList<>();
        for (int h = 0; h < 24; h++) {
            boolean isAvailable = request.getAvailableHours().contains(h);
            timeBlocks.add(AvailableTimeBlock.builder()
                    .studyRoom(savedRoom)
                    .hour(h)
                    .isAvailable(isAvailable)
                    .dayOfWeek("MONDAY") // 그냥 월요일로 (고정된 값으로)
                    .build());
        }
        availableTimeBlockRepository.saveAll(timeBlocks);

        return ResponseEntity.ok(savedRoom);
    }


    // 특정 카페에 등록된 스터디룸 목록 조회
    @GetMapping("/cafe/{studyCafeId}")
    public ResponseEntity<List<StudyRoom>> getRooms(@PathVariable Long studyCafeId) {
        return ResponseEntity.ok(studyRoomService.getRoomsByCafeId(studyCafeId));
    }
}

