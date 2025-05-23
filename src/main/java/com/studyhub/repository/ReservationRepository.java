package com.studyhub.repository;

import com.studyhub.domain.Reservation;
import com.studyhub.domain.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findFirstByUserIdAndStartTimeBeforeAndEndTimeAfterAndStatus(
            Long userId, LocalDateTime before, LocalDateTime after, ReservationStatus status
    );


}