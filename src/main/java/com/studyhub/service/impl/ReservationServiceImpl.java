package com.studyhub.service.impl;

import com.studyhub.domain.Reservation;
import com.studyhub.domain.StudyRoom;
import com.studyhub.domain.StudyCafe;
import com.studyhub.domain.enums.ReservationStatus;
import com.studyhub.repository.ReservationRepository;
import com.studyhub.repository.StudyRoomRepository;
import com.studyhub.repository.StudyCafeRepository;
import com.studyhub.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final StudyRoomRepository studyRoomRepository;
    private final StudyCafeRepository studyCafeRepository;

    @Override
    public Map<String, Object> getCurrentReservationDetails(Long userId) {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("🟡 [DEBUG] 현재 시각: " + now);
        System.out.println("🟡 [DEBUG] 세션 userId: " + userId);

        // 🔎 전체 예약 직접 출력 (디버깅용)
        List<Reservation> all = reservationRepository.findAll();
        for (Reservation r : all) {
            System.out.println("🔎 예약 ID: " + r.getId());
            System.out.println("🔎 사용자 ID: " + r.getUser().getId());
            System.out.println("🔎 상태: " + r.getStatus());
            System.out.println("🔎 시간 범위: " + r.getStartTime() + " ~ " + r.getEndTime());
        }

        Reservation reservation = reservationRepository
                .findFirstByUserIdAndStartTimeBeforeAndEndTimeAfterAndStatus(
                        userId, now, now, ReservationStatus.RESERVED
                ).orElse(null);

        if (reservation == null) {
            System.out.println("🔴 [DEBUG] 조건에 맞는 예약이 없습니다.");
            return null;
        }

        System.out.println("🟢 [DEBUG] 예약 ID: " + reservation.getId());

        StudyRoom room = studyRoomRepository.findById(reservation.getStudyRoom().getId())
                .orElseThrow(() -> new RuntimeException("스터디룸 없음"));

        StudyCafe cafe = studyCafeRepository.findById(room.getStudyCafe().getId())
                .orElseThrow(() -> new RuntimeException("스터디카페 없음"));

        Map<String, Object> result = new HashMap<>();
        result.put("reservationId", reservation.getId());
        result.put("cafeName", cafe.getName());
        result.put("roomName", room.getName());
        result.put("maxCapacity", room.getMaxCapacity());
        result.put("startTime", reservation.getStartTime());
        result.put("endTime", reservation.getEndTime());

        return result;
    }
}


