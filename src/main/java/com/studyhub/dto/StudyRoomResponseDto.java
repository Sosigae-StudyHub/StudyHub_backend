package com.studyhub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudyRoomResponseDto {
    private Long id;
    private String name;
    private int maxCapacity;
    private int price;
    private String equipmentInfo;
    private String preReservationNotice;
    private String postReservationNotice;
    private String cancelNotice;
    private String description;
    private Long studyCafeId;
}
