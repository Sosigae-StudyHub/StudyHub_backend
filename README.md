#  스터디룸 예약 시 포인트 차감 및 결제 내역 기록 기능  
### 브랜치 : feature/calendar
### 담당자 : 이가연 

- feature/charge-reservation  브랜치에서 파생됨
---
## ✅ 1. 날짜별 예약 요약 조회 API

### ✅ 기능 설명  
- 캘린더에서 날짜를 선택했을 때, 로그인한 사용자가 해당 날짜에 예약한 목록을 반환합니다.  
- 예약 ID, 스터디카페 이름, 스터디룸 이름, 예약 시간 등의 정보가 포함됩니다.  
- 이 API를 통해 프론트에서는 하단 예약 리스트를 출력할 수 있습니다.

### 🔗 URI  
```
GET /reservations/by-date?date=YYYY-MM-DD
````
- 세션 기반 (로그인 필수)

### 📥 요청 예시
```
/reservations/by-date?date=2025-04-26
```

### 📤 응답 예시
```json
[
  {
    "reservationId": 302,
    "cafeName": "신촌 스터디카페5",
    "roomName": "스터디룸11111",
    "startTime": "2025-04-26T14:00:00",
    "endTime": "2025-04-26T15:00:00"
  }
]
```

### ⚙️ 구현된 주요 로직 흐름
1. `ReservationController.getReservationsByDate()`로 요청 진입
2. 세션에서 userId 추출
3. 해당 날짜 기준으로 `ReservationRepository.findByUserIdAndDate()` 호출
4. `ReservationSummaryResponse` 리스트로 변환하여 반환
---

## ✅ 2. 예약 상세 조회 API

### ✅ 기능 설명  
- 사용자가 예약 리스트 중 하나를 클릭했을 때, 해당 예약의 상세 정보를 반환합니다.
- 본인의 예약만 접근 가능하며, 예약에 연결된 스터디룸의 공지사항과 환불 규정이 포함됩니다.

### 🔗 URI  
```
GET /reservations/{reservationId}/details
````
- 세션 기반 (로그인 필수)

### 📥 요청 예시
```
/reservations/302/details
```

### 📤 응답 예시
```json
{
  "cafeName": "신촌 스터디카페5",
  "roomName": "스터디룸11111",
  "roomCapacity": 6,
  "startTime": "2025-04-26T14:00:00",
  "endTime": "2025-04-26T15:00:00",
  "imageUrl": null,
  "preReservationNotice": "와이파이, 콘센트 제공",
  "cancelNotice": "당일 취소 시 환불 불가"
}
```

### ⚠️ 오류 응답 예시

| 상황             | 응답 코드 | 메시지                          |
|------------------|-----------|---------------------------------|
| 로그인 안됨      | 401       | `"로그인이 필요합니다."`        |
| 본인 예약 아님   | 404       | `"예약이 존재하지 않거나 권한이 없습니다."` 


### ⚙️ 구현된 주요 로직 흐름
1. `ReservationController.getReservationDetails()`로 요청 진입
2. 세션에서 userId 추출
3. `reservationId` + `userId`로 본인 예약인지 확인
4. `ReservationDetailResponse`로 변환 후 반환
   
---

## 📁 프로젝트 구조 및 역할

```text
src/main/java/com/studyhub/
├── controller/
│   └── ReservationController.java       # 예약 생성, 중복확인, 요약 조회, 상세 조회
│
├── domain/
│   └── Reservation.java
│   └── StudyRoom.java
│   └── StudyCafe.java
│   └── enums/
│       └── ReservationStatus.java
│
├── dto/
│   └── ReservationRequest.java               # 예약 요청 DTO
│   └── ReservationSummaryResponse.java       # 날짜별 예약 요약 응답 DTO
│   └── ReservationDetailResponse.java        # 예약 상세 응답 DTO
│
├── repository/
│   └── ReservationRepository.java            # 예약 중복/날짜별/상세 조회 쿼리 포함
│   └── StudyRoomRepository.java
│   └── StudyCafeRepository.java
│   └── UserRepository.java
│
├── service/
│   └── ReservationService.java
│
├── service/impl/
│   └── ReservationServiceImpl.java           # 예약 생성 + 조회 기능 구현
│
└── StudyhubBackendApplication.java
```

---

## 📝 TODO
- [x] 날짜별 예약 요약 API 구현  
- [x] 예약 상세 조회 API 구현
- [ ] 사업자 캘린더 API 구현
- [ ] 프론트 연동 (캘린더 및 상세 페이지 연결)
