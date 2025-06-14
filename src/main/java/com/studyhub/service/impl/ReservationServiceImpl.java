package com.studyhub.service.impl;

import com.studyhub.domain.Reservation;
import com.studyhub.domain.StudyCafe;
import com.studyhub.domain.StudyRoom;
import com.studyhub.domain.User;
import com.studyhub.domain.enums.ReservationStatus;
import com.studyhub.dto.ReservationDetailResponse;
import com.studyhub.dto.ReservationRequest;
import com.studyhub.dto.ReservationSummaryResponse;
import com.studyhub.repository.ReservationRepository;
import com.studyhub.repository.StudyCafeRepository;
import com.studyhub.repository.StudyRoomRepository;
import com.studyhub.repository.UserRepository;
import com.studyhub.service.PaymentHistoryService;
import com.studyhub.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final StudyRoomRepository studyRoomRepository;
    private final PaymentHistoryService paymentHistoryService; // ✅ 포인트 결제 서비스 주입(가연)
    private final StudyCafeRepository studyCafeRepository;

    // ✅ 예약 생성(지우)
    @Override
    @Transactional      //예약 생성과 결제 이력이 하나의 트랜잭션으로 묶음
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

        reservationRepository.save(reservation); // ✅ 1. 예약 정보 저장

        paymentHistoryService.recordStudyRoomPayment(user, reservation); // ✅ 2. 포인트 차감 및 결제 이력 저장
    }

    // ✅ 예약 겹침 여부만 확인(지우)
    @Override
    public boolean isOverlappingReservation(Long roomId, LocalDateTime start, LocalDateTime end) {
        return reservationRepository.existsOverlappingReservation(roomId, start, end);
    }

    // ✅ 날짜별 예약 요약 목록 반환(가연) - 사용자 캘린더 용
    @Override
    public List<ReservationSummaryResponse> getReservationsByDate(Long userId, LocalDate date) {
        // 날짜의 00:00 ~ 23:59까지 범위 설정
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        // 예약 조회
        List<Reservation> reservations = reservationRepository.findByUserIdAndDate(userId, startOfDay, endOfDay);

        // DTO 변환
        return reservations.stream()
                .map(r -> new ReservationSummaryResponse(
                        r.getId(),
                        r.getStudyRoom().getStudyCafe().getName(),
                        r.getStudyRoom().getName(),
                        r.getStartTime(),
                        r.getEndTime()
                ))
                .toList();
    }

    // ✅ 예약 상세 정보 반환 (가연)
    @Override
    public ReservationDetailResponse getReservationDetails(Long reservationId, Long userId) {
        Reservation reservation = reservationRepository.findByIdAndUserId(reservationId, userId)
                .orElseThrow(() -> new NoSuchElementException("예약이 존재하지 않거나 권한이 없습니다."));

        StudyRoom room = reservation.getStudyRoom();

        return new ReservationDetailResponse(
                room.getStudyCafe().getName(),
                room.getName(),
                room.getMaxCapacity(),
                reservation.getStartTime(),
                reservation.getEndTime(),
                null, // imageUrl은 아직 미지원
                room.getPreReservationNotice(),
                room.getCancelNotice()
        );
    }

    // ✅ 마이페이지에서 현재 예약 내역 보여주기 (가연)
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