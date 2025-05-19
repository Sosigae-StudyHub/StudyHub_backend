package com.studyhub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudyCafeSimpleDto {
    private Long id;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
}
