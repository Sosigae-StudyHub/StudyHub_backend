package com.studyhub.dto;

import lombok.Data;

import java.util.List;

@Data
public class StudyRoomRequest {
    private Long id;
    private String name; // 스터디룸 이름 (필수)
    private int maxCapacity; // 최대 수용 인원 (필수)
    private int price; // 이용 요금 (필수)
    private Long studyCafeId; // 소속 스터디카페 ID (필수)

    private String equipmentInfo; // 보유 장비 (선택)
    private String preReservationNotice; // 예약 전 공지 (선택)
    private String postReservationNotice; // 예약 후 공지 (선택)
    private String cancelNotice; // 취소/환불 정책 (선택)

    private List<Integer> availableHours;
    private Long ownerId; // 사업자 본인 ID 확인용
    private String description; // 스터디룸 소개
}
