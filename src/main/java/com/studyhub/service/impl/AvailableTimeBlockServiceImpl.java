package com.studyhub.service.impl;

import com.studyhub.domain.AvailableTimeBlock;
import com.studyhub.domain.Reservation;
import com.studyhub.repository.AvailableTimeBlockRepository;
import com.studyhub.repository.ReservationRepository;
import com.studyhub.service.AvailableTimeBlockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AvailableTimeBlockServiceImpl implements AvailableTimeBlockService {

    private final AvailableTimeBlockRepository availableTimeBlockRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public List<Integer> getUnavailableHours(Long roomId) {
        return availableTimeBlockRepository.findByStudyRoomIdAndIsAvailable(roomId, true)
                .stream()
                .map(AvailableTimeBlock::getHour)
                .collect(Collectors.toList());
    }

    @Override
    public List<Integer> getFullyUnavailableHours(Long roomId, LocalDate date) {
        // 1. 사용불가 블록
        Set<Integer> unavailableFromBlocks = new HashSet<>(
                availableTimeBlockRepository.findByStudyRoomIdAndIsAvailable(roomId, true)
                        .stream()
                        .map(AvailableTimeBlock::getHour)
                        .toList()
        );

        // 2. 예약된 시간 전개
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        List<Reservation> reservations = reservationRepository.findByRoomIdAndDate(roomId, startOfDay, endOfDay);

        Set<Integer> reservedHours = new HashSet<>();
        for (Reservation r : reservations) {
            int start = r.getStartTime().getHour();
            int end = r.getEndTime().getHour();
            for (int h = start; h < end; h++) {
                reservedHours.add(h);
            }
        }

        unavailableFromBlocks.addAll(reservedHours);
        return unavailableFromBlocks.stream().sorted().toList();
    }
//    @Override
//    public List<Integer> getFullyUnavailableHours(Long roomId, LocalDate date) {
//        // 1. 사용불가 hour
//        Set<Integer> unavailableFromBlocks = new HashSet<>(
//                availableTimeBlockRepository.findByStudyRoomIdAndIsAvailable(roomId, true)
//                        .stream()
//                        .map(AvailableTimeBlock::getHour)
//                        .toList()
//        );
//보유포인트 865996p
//        // 2. 예약된 hour
//        LocalDateTime startOfDay = date.atStartOfDay();
//        LocalDateTime endOfDay = startOfDay.plusDays(1);
//
//        Set<Integer> reservedHours = new HashSet<>(
//                reservationRepository.findReservedHoursByRoomIdAndDate(roomId, startOfDay, endOfDay)
//        );
//
//        unavailableFromBlocks.addAll(reservedHours);
//        return unavailableFromBlocks.stream().sorted().toList();
//    }
}
