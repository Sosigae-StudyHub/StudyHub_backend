#  스터디룸 예약 시 포인트 차감 및 결제 내역 기록 기능  
### 브랜치 : feature/charge-reservation  
### 담당자 : 이가연 (류지우 구현 코드와 merge한 후 개발 함)

---
## ✅ 1. 스터디룸 예약 + 포인트 차감 기능

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

---

## ⚙️ 구현된 주요 로직 흐름

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

## 🧱 패키지 구조 리팩토링

이번 기능 구현과 함께 기존 단일 클래스였던 `ReservationService`를 다음과 같이 분리하여  
**전체 서비스 구조 통일성 유지 및 테스트 확장성 향상**을 반영했습니다:

| 변경 내용 |
|-----------|
| `ReservationService` 인터페이스 생성 |
| `ReservationServiceImpl` 클래스로 기능 구현 이동 |
| 모든 구현 클래스(`*ServiceImpl`)를 `impl/` 서브패키지로 이동하여 역할 분리 명확화 |

---

## 📁 프로젝트 구조 및 역할

```text
src/main/java/com/studyhub/
├── controller/
│   └── ReservationController.java       # 예약 생성 API, 중복 확인 API

├── domain/
│   └── Reservation.java
│   └── StudyRoom.java
│   └── PaymentHistory.java
│   └── enums/
│       └── ReservationStatus.java
│       └── PaymentType.java

├── repository/
│   └── ReservationRepository.java
│   └── StudyRoomRepository.java
│   └── PaymentHistoryRepository.java
│   └── UserRepository.java

├── service/
│   └── ReservationService.java
│   └── PaymentHistoryService.java

├── service/impl/
│   └── ReservationServiceImpl.java       # 예약 생성 + 결제 호출
│   └── PaymentHistoryServiceImpl.java    # 포인트 차감 + 결제 저장

├── dto/
│   └── ReservationRequest.java           # 예약 요청 DTO

└── StudyhubBackendApplication.java       # 메인 클래스
```

---

## 📝 TODO
- [ ] 사용자 결제 내역 조회 API 구현
- [ ] 프론트 연동
