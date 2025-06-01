package com.studyhub.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class RevenueStatsDto {
    private LocalDate date;
    private Long totalAmount;
}