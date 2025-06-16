package com.studyhub.controller;

import com.studyhub.dto.RevenueStatsDto;
import com.studyhub.service.RevenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/revenue")
@RequiredArgsConstructor
public class RevenueController {

    private final RevenueService revenueService;

    // 일별
    @GetMapping("/daily")
    public List<RevenueStatsDto> getDailyRevenue(
            @RequestParam("cafeId") Long cafeId,
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {

        return revenueService.getDailyRevenue(cafeId, from, to);
    }

    // 주별
    @GetMapping("/weekly")
    public List<RevenueStatsDto> getWeeklyRevenue(
            @RequestParam Long cafeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        return revenueService.getWeeklyRevenue(cafeId, from, to);
    }

    // 월별
    @GetMapping("/monthly")
    public List<RevenueStatsDto> getMonthlyRevenue(
            @RequestParam Long cafeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        return revenueService.getMonthlyRevenue(cafeId, from, to);
    }

    // 연도별
    @GetMapping("/yearly")
    public List<RevenueStatsDto> getYearlyRevenue(
            @RequestParam Long cafeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        return revenueService.getYearlyRevenue(cafeId, from, to);
    }
}
