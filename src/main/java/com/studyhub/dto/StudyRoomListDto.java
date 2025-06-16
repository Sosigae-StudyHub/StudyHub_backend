package com.studyhub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StudyRoomListDto {
    private Long id;
    private String name;
    private int maxCapacity;
    private int price;
    private List<Integer> availableHours;
}
