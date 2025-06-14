package com.studyhub.service.impl;

import com.studyhub.domain.Reservation;
import com.studyhub.domain.StudyRoom;
import com.studyhub.domain.User;
import com.studyhub.domain.enums.ReservationStatus;
import com.studyhub.dto.ReservationRequest;
import com.studyhub.repository.ReservationRepository;
import com.studyhub.repository.StudyRoomRepository;
import com.studyhub.repository.UserRepository;
import com.studyhub.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final StudyRoomRepository studyRoomRepository;

    // ✅ 예약 생성
    @Override
    public void makeReservation(ReservationRequest request) {
        boolean isOverlap = reservationRepository.existsOverlappingReservation(
                request.getStudyRoomId(),
                request.getStartTime(),
                request.getEndTime()
        );

        if (isOverlap) {
            throw new IllegalStateException("이미 예약된 시간입니다.");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));
        StudyRoom room = studyRoomRepository.findById(request.getStudyRoomId())
                .orElseThrow(() -> new IllegalArgumentException("스터디룸 없음"));

        Reservation reservation = Reservation.builder()
                .user(user)
                .studyRoom(room)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .status(ReservationStatus.RESERVED)
                .build();

        reservationRepository.save(reservation);
    }

    // ✅ 예약 겹침 여부만 확인
    @Override
    public boolean isOverlappingReservation(Long roomId, LocalDateTime start, LocalDateTime end) {
        return reservationRepository.existsOverlappingReservation(roomId, start, end);
    }
}
