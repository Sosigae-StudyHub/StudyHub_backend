
package com.studyhub.service;

import com.studyhub.dto.RevenueStatsDto;
import java.time.LocalDateTime;
import java.util.List;

public interface RevenueService {
    List<RevenueStatsDto> getDailyRevenue(Long cafeId, LocalDateTime from, LocalDateTime to);
    List<RevenueStatsDto> getWeeklyRevenue(Long cafeId, LocalDateTime from, LocalDateTime to);
    List<RevenueStatsDto> getMonthlyRevenue(Long cafeId, LocalDateTime from, LocalDateTime to);
    List<RevenueStatsDto> getYearlyRevenue(Long cafeId, LocalDateTime from, LocalDateTime to);
}

