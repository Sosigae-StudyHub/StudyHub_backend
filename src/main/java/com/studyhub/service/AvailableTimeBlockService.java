package com.studyhub.service;

import java.time.LocalDate;
import java.util.List;

public interface AvailableTimeBlockService {
    List<Integer> getUnavailableHours(Long roomId);
    List<Integer> getFullyUnavailableHours(Long roomId, LocalDate date); // 추가
}
