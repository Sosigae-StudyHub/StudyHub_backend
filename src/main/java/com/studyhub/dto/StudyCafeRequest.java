package com.studyhub.dto;

import lombok.Data;

@Data
public class StudyCafeRequest {
    private Long id;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private String contact;
    private String businessHour;
    private Long ownerId;
    private String notice;
    private String reservationCheckMessage;
}
