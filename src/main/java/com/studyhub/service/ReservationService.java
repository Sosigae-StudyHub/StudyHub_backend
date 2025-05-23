package com.studyhub.service;

import java.util.Map;

public interface ReservationService {
    Map<String, Object> getCurrentReservationDetails(Long userId);
}