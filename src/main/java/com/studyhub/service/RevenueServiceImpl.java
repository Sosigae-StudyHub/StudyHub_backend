package com.studyhub.service;

import com.studyhub.dto.RevenueStatsDto;
import com.studyhub.repository.RevenueRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RevenueServiceImpl implements RevenueService {

    private final RevenueRepository revenueRepository;

    public RevenueServiceImpl(RevenueRepository revenueRepository) {
        this.revenueRepository = revenueRepository;
    }

    // 일별
    @Override
    public List<RevenueStatsDto> getDailyRevenue(Long cafeId, LocalDateTime from, LocalDateTime to) {
        Timestamp fromTimestamp = Timestamp.valueOf(from);
        Timestamp toTimestamp = Timestamp.valueOf(to);

        List<Object[]> result = revenueRepository.findDailyRevenue(cafeId, fromTimestamp, toTimestamp);
        System.out.println("쿼리 날짜 from: " + from);
        System.out.println("쿼리 날짜 to: " + to);
        System.out.println("쿼리 카페 ID: " + cafeId);
        System.out.println("DB 조회 결과 수: " + result.size());

        return result.stream()
                .map(row -> {
                    LocalDate localDate = ((Timestamp) row[0]).toLocalDateTime().toLocalDate();
                    return new RevenueStatsDto(localDate, ((Number) row[1]).longValue());
                })
                .toList();
    }

    // 주별
    @Override
    public List<RevenueStatsDto> getWeeklyRevenue(Long cafeId, LocalDateTime from, LocalDateTime to) {
        return revenueRepository.findWeeklyRevenue(cafeId, Timestamp.valueOf(from), Timestamp.valueOf(to))
                .stream()
                .map(row -> new RevenueStatsDto(
                        LocalDate.parse((String) row[0]),
                        ((BigDecimal) row[1]).longValue()))
                .toList();
    }

    // 월별
    @Override
    public List<RevenueStatsDto> getMonthlyRevenue(Long cafeId, LocalDateTime from, LocalDateTime to) {
        return revenueRepository.findMonthlyRevenue(cafeId, Timestamp.valueOf(from), Timestamp.valueOf(to))
                .stream()
                .map(row -> new RevenueStatsDto(
                        LocalDate.parse((String) row[0] + "-01"),
                        ((BigDecimal) row[1]).longValue()))
                .toList();
    }

    // 년도별
    @Override
    public List<RevenueStatsDto> getYearlyRevenue(Long cafeId, LocalDateTime from, LocalDateTime to) {
        return revenueRepository.findYearlyRevenue(cafeId, Timestamp.valueOf(from), Timestamp.valueOf(to))
                .stream()
                .map(row -> new RevenueStatsDto(
                        LocalDate.parse((String) row[0] + "-01-01"),
                        ((BigDecimal) row[1]).longValue()))
                .toList();
    }

}
