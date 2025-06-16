package com.studyhub.controller;

import com.studyhub.dto.StudyRoomResponse;
import com.studyhub.domain.AvailableTimeBlock;
import com.studyhub.domain.StudyCafe;
import com.studyhub.domain.StudyRoom;
import com.studyhub.dto.StudyRoomListDto;
import com.studyhub.dto.StudyRoomRequest;
import com.studyhub.dto.StudyRoomResponseDto;
import com.studyhub.repository.AvailableTimeBlockRepository;
import com.studyhub.service.StudyCafeService;
import com.studyhub.service.StudyRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class StudyRoomController {

    private final StudyRoomService studyRoomService;
    private final StudyCafeService studyCafeService;
    private final AvailableTimeBlockRepository availableTimeBlockRepository;


    // 스터디룸 등록
    @PostMapping("/register")
    public ResponseEntity<StudyRoomResponseDto> registerRoom(@RequestBody StudyRoomRequest request) {
        StudyCafe cafe = studyCafeService.getCafeById(request.getStudyCafeId());

        // 소유자 검증
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
                .description(request.getDescription())
                .build();

        // 저장
        StudyRoom savedRoom = studyRoomService.registerAndReturn(room);

        // 예약 가능 시간 설정 (0~23 전체 순회 후 저장)
        List<AvailableTimeBlock> timeBlocks = new ArrayList<>();
        for (int h = 0; h < 24; h++) {
            boolean isAvailable = request.getAvailableHours().contains(h);
            timeBlocks.add(AvailableTimeBlock.builder()
                    .studyRoom(savedRoom)
                    .hour(h)
                    .isAvailable(isAvailable)
                    .dayOfWeek("MONDAY") // 고정값
                    .build());
        }
        availableTimeBlockRepository.saveAll(timeBlocks);

        // DTO로 응답
        StudyRoomResponseDto response = StudyRoomResponseDto.builder()
                .id(savedRoom.getId())
                .name(savedRoom.getName())
                .maxCapacity(savedRoom.getMaxCapacity())
                .price(savedRoom.getPrice())
                .equipmentInfo(savedRoom.getEquipmentInfo())
                .preReservationNotice(savedRoom.getPreReservationNotice())
                .postReservationNotice(savedRoom.getPostReservationNotice())
                .cancelNotice(savedRoom.getCancelNotice())
                .description(savedRoom.getDescription())
                .studyCafeId(cafe.getId()) // 추가
                .build();

        return ResponseEntity.ok(response);
    }


    // 스터디룸 수정
    @PutMapping("/update")
    public ResponseEntity<StudyRoomResponseDto> updateRoom(@RequestBody StudyRoomRequest request) {
        StudyCafe cafe = studyCafeService.getCafeById(request.getStudyCafeId());

        if (!cafe.getOwner().getId().equals(request.getOwnerId())) {
            throw new AccessDeniedException("해당 스터디카페의 소유자가 아닙니다.");
        }

        StudyRoom room = StudyRoom.builder()
                .id(request.getId())
                .name(request.getName())
                .maxCapacity(request.getMaxCapacity())
                .price(request.getPrice())
                .equipmentInfo(request.getEquipmentInfo())
                .studyCafe(cafe)
                .preReservationNotice(request.getPreReservationNotice())
                .postReservationNotice(request.getPostReservationNotice())
                .cancelNotice(request.getCancelNotice())
                .description(request.getDescription())
                .build();

        StudyRoom updated = studyRoomService.updateRoomWithTimeBlocks(room, request.getAvailableHours());

        StudyRoomResponseDto response = StudyRoomResponseDto.builder()
                .id(updated.getId())
                .name(updated.getName())
                .maxCapacity(updated.getMaxCapacity())
                .price(updated.getPrice())
                .equipmentInfo(updated.getEquipmentInfo())
                .preReservationNotice(updated.getPreReservationNotice())
                .postReservationNotice(updated.getPostReservationNotice())
                .cancelNotice(updated.getCancelNotice())
                .description(updated.getDescription())
                .studyCafeId(cafe.getId())
                .build();

        return ResponseEntity.ok(response);
    }

    // 스터디룸 삭제
    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteRoom(
            @PathVariable Long roomId,
            @RequestParam Long ownerId) {

        studyRoomService.deleteRoomWithValidation(roomId, ownerId);
        return ResponseEntity.ok().build();
    }


    // 특정 카페에 등록된 스터디룸 목록 조회
    @GetMapping("/cafe/{studyCafeId}")
    public ResponseEntity<List<StudyRoomResponseDto>> getRooms(@PathVariable Long studyCafeId) {
        List<StudyRoom> rooms = studyRoomService.getRoomsByCafeId(studyCafeId);

        List<StudyRoomResponseDto> response = rooms.stream()
                .map(room -> {
                    List<Integer> availableHours = room.getAvailableTimes().stream()
                            .filter(AvailableTimeBlock::isAvailable)
                            .map(AvailableTimeBlock::getHour)
                            .toList();

                    return StudyRoomResponseDto.builder()
                            .id(room.getId())
                            .name(room.getName())
                            .maxCapacity(room.getMaxCapacity())
                            .price(room.getPrice())
                            .equipmentInfo(room.getEquipmentInfo())
                            .preReservationNotice(room.getPreReservationNotice())
                            .postReservationNotice(room.getPostReservationNotice())
                            .cancelNotice(room.getCancelNotice())
                            .description(room.getDescription())
                            .studyCafeId(room.getStudyCafe().getId())
                            .availableHours(availableHours) // 이 필드는 StudyRoomResponseDto에 새로 추가 필요
                            .build();
                })
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-cafe/{cafeId}")
    public ResponseEntity<List<StudyRoomResponse>> getRoomsResponseByCafeId(@PathVariable Long cafeId) {
        List<StudyRoomResponse> response = studyRoomService.getRoomsResponseByCafeId(cafeId);
        return ResponseEntity.ok(response);
    }
}
