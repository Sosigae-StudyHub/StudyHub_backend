package com.studyhub.repository;

import com.studyhub.domain.Reservation;
import com.studyhub.domain.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("""
    SELECT r FROM Reservation r
    WHERE r.studyRoom.id = :roomId
    AND r.startTime >= :startOfDay
    AND r.startTime < :endOfDay
""")
    List<Reservation> findByRoomIdAndDate(
            @Param("roomId") Long roomId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );


    @Query("""
        SELECT DISTINCT EXTRACT(HOUR FROM r.startTime)
        FROM Reservation r
        WHERE r.studyRoom.id = :roomId
          AND r.startTime >= :startOfDay
          AND r.startTime < :endOfDay
    """)
    List<Integer> findReservedHoursByRoomIdAndDate(
            @Param("roomId") Long roomId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );

    @Query("""
        SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END
        FROM Reservation r
        WHERE r.studyRoom.id = :roomId
          AND r.startTime < :end
          AND r.endTime > :start
    """)
    boolean existsOverlappingReservation(
            @Param("roomId") Long roomId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    // ✅ 사용자 ID와 날짜 기준으로 예약 리스트를 조회하는 쿼리 추가 - 개인 이용자 캘린더 용 (가연)
    // startTime이 해당 날짜 자정부터 다음날 자정 전까지인 예약만 포함
    @Query("""
    SELECT r FROM Reservation r
    WHERE r.user.id = :userId
      AND r.startTime >= :startOfDay
      AND r.startTime < :endOfDay
    ORDER BY r.startTime
    """)
    List<Reservation> findByUserIdAndDate(
            @Param("userId") Long userId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );

    // ✅ 로그인한 사용자 본인의 예약을 예약 ID로 조회하는 메서드  예약 내역 상세보기 (가연)
    @Query("""
    SELECT r FROM Reservation r
    JOIN FETCH r.studyRoom sr
    JOIN FETCH sr.studyCafe
    WHERE r.id = :reservationId AND r.user.id = :userId
    """)
    Optional<Reservation> findByIdAndUserId(
            @Param("reservationId") Long reservationId,
            @Param("userId") Long userId
    );

    Optional<Reservation> findFirstByUserIdAndStartTimeBeforeAndEndTimeAfterAndStatus(
            Long userId, LocalDateTime before, LocalDateTime after, ReservationStatus status
    );

    // ✅ 사업자 캘린더 - 스터디카페 별 예약 내역 불러오기 (현재 시간 기준 앞으로의 예약 내역만 가져옴)
    @Query(value = """
    SELECT r.* FROM reservations r
    JOIN study_rooms sr ON r.study_room_id = sr.id
    JOIN study_cafes sc ON sr.study_cafe_id = sc.id
    JOIN users u ON r.user_id = u.id
    WHERE sc.id = :cafeId
    AND r.end_time > SYSDATE
    ORDER BY r.end_time ASC
    """, nativeQuery = true)
    List<Reservation> findFutureReservationsByCafeId(@Param("cafeId") Long cafeId);

}
