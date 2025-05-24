# 💸 스터디룸 예약 시 포인트 차감 및 결제 내역 기록 기능  
### 브랜치 : feature/charge-reservation  
### 담당자 : 이가연 (류지우 구현 코드와 merge한 후 개발 함 : feature/charge + feature/reservation)

---

## 1. 🧱 패키지 구조 리팩토링

이번 기능 구현과 함께 기존 단일 클래스였던 `ReservationService`를 다음과 같이 분리하여  
**전체 서비스 구조 통일성 유지 및 테스트 확장성 향상**을 반영했습니다:

| 변경 내용 |
|-----------|
| `ReservationService` 인터페이스 생성 |
| `ReservationServiceImpl` 클래스로 기능 구현 이동 |
| 모든 구현 클래스(`*ServiceImpl`)를 `impl/` 서브패키지로 이동하여 역할 분리 명확화 |

---
## ✅ 2.  스터디룸 예약 + 포인트 차감 기능

### ✅ 기능 설명  
- 사용자가 스터디룸 예약 요청을 보냈을 때, 시간 겹침이 없고 포인트가 충분한 경우 예약이 생성됩니다.  
- 예약이 성공하면, 스터디룸 가격만큼 포인트가 차감되고 `PaymentHistory` 테이블에 결제 이력이 저장됩니다.  
- 포인트가 부족하거나 시간이 겹치면 409 응답과 함께 예외 메시지를 반환합니다.

### 🔗 URI  
```
POST /reservations/make
````
- 세션 기반 (로그인 필수)

### 📥 요청 예시
```json
{
  "userId": 23,
  "studyRoomId": 21,
  "startTime": "2025-05-25T01:50:00",
  "endTime": "2025-05-25T02:30:00"
}
```

### 📤 응답 예시

#### ✅ 예약 성공 (200 OK)
```
HTTP/1.1 200 OK
```

#### ❌ 예약 시간 중복 (409 Conflict)
```json
"이미 예약된 시간입니다."
```

#### ❌ 포인트 부족 (409 Conflict)
```json
"포인트가 부족합니다."
```

### ⚙️ 구현된 주요 로직 흐름

1. `ReservationController`로 요청 진입
2. `ReservationServiceImpl.makeReservation()`에서:
   - 예약 가능 시간 확인
   - 사용자 및 스터디룸 조회
   - 예약 정보 저장
   - `PaymentHistoryService.recordStudyRoomPayment()` 호출
3. `recordStudyRoomPayment()` 내부에서:
   - 포인트 부족 여부 확인
   - 포인트 차감
   - 결제 이력 저장 (PaymentType = STUDY_ROOM)

---
## ✅ 3. 사용자 결제 내역 조회 API

### ✅ 기능 설명  
- 로그인된 사용자의 포인트 충전 및 스터디룸 결제 내역을 기간, 유형 기준으로 필터링하여 조회할 수 있습니다.  
- 기간(`from`, `to`) 또는 결제 유형(`CHARGE`, `STUDY_ROOM`)을 선택적으로 쿼리 파라미터로 전달할 수 있으며,  
  필터 조건이 없을 경우 전체 결제 내역이 반환됩니다.

### 🔗 URI  
```
GET /payments/history
```
- 세션 기반 (로그인 필수)

### 📥 요청 예시
```http
/payments/history?from=2025-05-01&to=2025-05-31&type=STUDY_ROOM
```
### 📤 응답 예시
```json
[
  {
    "date": "2025-05-20",
    "amount": 4000,
    "type": "STUDY_ROOM",
    "reservationId": 202
  },
  {
    "date": "2025-05-10",
    "amount": 5000,
    "type": "CHARGE",
    "reservationId": null
  }
]
```
#### ❌ 비로그인 사용자 (401 Unauthorized)
```
HTTP/1.1 401 Unauthorized
```
### ⚙️ 구현된 주요 로직 흐름

1. `PaymentVerificationController`로 요청 진입
2. `userId`를 세션에서 추출하여 인증 확인
3. 필터 조건(`from`, `to`, `type`)을 기반으로 분기 처리
4. `PaymentHistoryService`에서 결제 내역 조회
5. `PaymentHistoryResponse` DTO로 매핑 후 반환

### 🧪 테스트 시나리오 커버

| 테스트 조건 | URI 예시 |
|-------------|----------|
| 전체 조회 | `/payments/history` |
| 1개월 조회 | `/payments/history?from=2025-04-25` |
| 유형만 조회 | `/payments/history?type=CHARGE` |
| 기간 + 유형 | `/payments/history?from=2025-04-01&to=2025-05-25&type=STUDY_ROOM` |
| 미로그인 시도 | 쿠키 없이 요청 → `401 Unauthorized` 응답 |


---
## 📁 프로젝트 구조 및 역할

```text
src/main/java/com/studyhub/
├── controller/
│   └── ReservationController.java       # 예약 생성 API, 중복 확인 API
│   └── PaymentVerificationController.java   # 결제 검증 + 결제 내역 조회
│ 
├── domain/
│   └── Reservation.java
│   └── StudyRoom.java
│   └── PaymentHistory.java
│   └── enums/
│       └── ReservationStatus.java
│       └── PaymentType.java
│ 
├── dto/
│   └── PaymentHistoryResponse.java          # 결제 내역 응답 DTO
│   └── PaymentVerificationRequest.java      # 포트원 결제 검증 요청 DTO
│ 
├── repository/
│   └── ReservationRepository.java
│   └── StudyRoomRepository.java
│   └── UserRepository.java
│   └── PaymentHistoryRepository.java        # 결제 내역 조회용 JPA 추
│ 
├── service/
│   └── ReservationService.java
│   └── PaymentHistoryService.java
│   └── PaymentHistoryService.java
│ 
├── service/impl/
│   └── ReservationServiceImpl.java       # 예약 생성 + 결제 호출
│   └── PaymentHistoryServiceImpl.java    # 포인트 차감 + 결제 저장/ 내역 조회, 필터 분기 처리
│ 
├── dto/
│   └── ReservationRequest.java           # 예약 요청 DTO

└── StudyhubBackendApplication.java       # 메인 클래스
```

---

## 📝 TODO
- [x] 사용자 결제 내역 조회 API 구현 완료  
- [ ] 프론트 연동 (결제 내역 화면에서 해당 API 호출)
