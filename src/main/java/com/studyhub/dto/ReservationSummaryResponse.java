package com.studyhub.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

// ✅ 사용자가 날짜를 선택했을 때 보여줄 예약 요약 정보 DTO (가연)
@Getter
@AllArgsConstructor
public class ReservationSummaryResponse {
    private Long reservationId;     //리스트에서 예약 상세 페이지로 이동할 때 사용되는 ID
    private String cafeName;        //StudyRoom → StudyCafe를 통해 조회
    private String roomName;        //StudyRoom의 이름
    //예약 시간 표시용
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
