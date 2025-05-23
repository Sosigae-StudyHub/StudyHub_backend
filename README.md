# 💳 StudyHub - 포트원 V2 결제 검증 API
### 브랜치 : feature/mypage
### 담당자 : 이가연

---
## ✅ 1. 사용자 프로필 조회 API

### URI: `GET /users/profile`
### 방식: `GET`
### 인증: 세션 기반 (로그인 필수)

### ✅ 기능 설명

- 현재 로그인한 사용자의 프로필 정보를 반환합니다.
- 사용자 타입(USER/OWNER)에 따라 반환 필드가 다릅니다.

### ✅ 응답 예시 (개인 사용자)

```json
{
  "name": "이가연",
  "email": "gayeon@email.com",
  "phone": "010-1234-5678",
  "userType": "USER",
  "point": 10000
}
```
### ✅ 응답 예시 (사업자 사용자)
```
{
  "name": "장하연",
  "email": "owner@email.com",
  "phone": "010-1111-2222",
  "userType": "OWNER",
  "businessNumber": "111-22-33333"
}
```
### ❌ 응답 예시 (401 Unauthorized)
```
HTTP/1.1 401 Unauthorized
Body: "로그인이 필요합니다."
```

---
## ✅ 2. 현재 이용 중 예약 조회 API

### URI: `GET /reservations/current`
### 방식: `GET`
### 인증: 세션 기반 (로그인 필수)

### ✅ 기능 설명

- 사용자가 현재 시간 기준으로 이용 중인 스터디룸이 있는 경우 해당 예약 정보를 반환합니다.
- 없을 경우 204 No Content 응답을 반환합니다.

### 응답 예시 (200 OK)
```
{
  "reservationId": 46,
  "cafeName": "신촌 스터디카페5",
  "roomName": "스터디룸 A",
  "maxCapacity": 6,
  "startTime": "2025-05-24T01:24:13",
  "endTime": "2025-05-24T01:53:01"
}
```

```
### ❌ 응답 예시 (204 No Content)
```
HTTP/1.1 204 No Content
```

---
## ⚙️ 구현 파일 구조

| 기능               | 관련 클래스 및 파일 목록 |
|--------------------|---------------------------|
| 사용자 프로필 조회    | `UserController`, `UserService`, `UserServiceImpl`, `UserRepository` |
| 현재 예약 조회        | `ReservationController`, `ReservationService`, `ReservationServiceImpl`, `ReservationRepository`, `StudyRoomRepository`, `StudyCafeRepository` |

---

## 📁 프로젝트 구조 및 역할

```text
src/main/java/com/studyhub/
├── controller/              # 요청 진입 지점 (REST API 정의)
│   └── UserController.java
│   └── ReservationController.java

├── domain/                  # JPA 엔티티 클래스
│   └── User.java
│   └── Reservation.java
│   └── StudyRoom.java
│   └── StudyCafe.java
│   └── enums/               # Enum 타입 (UserType, ReservationStatus 등)

├── repository/              # JPA Repository 인터페이스
│   └── UserRepository.java
│   └── ReservationRepository.java
│   └── StudyRoomRepository.java
│   └── StudyCafeRepository.java

├── service/                 # 비즈니스 로직 계층
│   └── UserService.java / UserServiceImpl.java
│   └── ReservationService.java / ReservationServiceImpl.java

├── dto/                     # 요청/응답 DTO 또는 Projection
│   └── CurrentReservationProjection.java (사용 시)

└── StudyhubBackendApplication.java  # 메인 클래스

---##📝 TODO
- [ ] 프론트 연동
