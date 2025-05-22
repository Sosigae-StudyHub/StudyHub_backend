# 💳 StudyHub - 포트원 V2 결제 검증 API
### 브랜치 : feature/charge
### 담당자 : 이가연

- 목적: 포트원 결제 검증 및 포인트 적립 API 구현
- 이 모듈은 포트원(PortOne) V2 REST API를 기반으로, 클라이언트 결제 완료 이후 해당 결제가 실제로 완료되었는지를 검증하고,  
검증에 성공하면 사용자에게 포인트를 적립하는 기능을 제공합니다.
---

## 📦 주요 기능

| 기능 | 설명 |
|------|------|
| `GET /payments/{paymentId}` 호출 | 포트원 V2 REST API로 결제 단건 조회 |
| 결제 금액, 상태 검증 | 금액 일치 & 상태 `PAID` 여부 확인 |
| 사용자 포인트 적립 | 결제 성공 시 해당 사용자 포인트 증가 |
| 결제 내역 기록 | `payment_history` 테이블에 저장 |
---

## 🔐 환경 변수
```properties
portone.api-secret=${PORTONE_V2_API_SECRET}
```
- PORTONE_V2_API_SECRET은 포트원 콘솔에서 발급받은 V2 전용 시크릿 키

---
## 📁 프로젝트 구조 및 역할
```
studyhub-backend/
├── src/
│   └── main/
│       ├── java/com/studyhub/
│       │   ├── controller/
│       │   │   └── PaymentVerificationController.java   # 결제 검증 요청을 처리하는 REST API 컨트롤러
│       │   │
│       │   ├── dto/
│       │   │   └── PaymentVerificationRequest.java      # 결제 검증 요청 바디(paymentId, amount, userId) DTO
│       │   │
│       │   ├── service/
│       │   │   ├── PortOneService.java                  # 포트원 결제 단건 조회 서비스 인터페이스
│       │   │   └── PaymentHistoryService.java           # 포인트 적립 및 결제 기록 저장 서비스 인터페이스
│       │   │
│       │   ├── service/impl/
│       │   │   ├── PortOneServiceImpl.java              # 포트원 V2 API 호출 및 결제 검증 로직 구현
│       │   │   └── PaymentHistoryServiceImpl.java       # 사용자 포인트 증가 및 결제 내역 저장 로직 구현
│       │   │
│       │   ├── domain/
│       │   │   ├── User.java                            # 사용자 정보 및 현재 포인트 보유 상태 관리
│       │   │   └── PaymentHistory.java                  # 결제 이력 테이블 도메인
│       │   │
│       │   ├── repository/
│       │   │   ├── UserRepository.java                  # 사용자 조회용 JPA 리포지토리
│       │   │   └── PaymentHistoryRepository.java        # 결제 이력 저장용 리포지토리
│       │   │
│       │   └── config/
│       │       └── PortOneProperties.java               # 포트원 API 시크릿 설정을 위한 환경변수 클래스 (@ConfigurationProperties)
│       │
│       └── resources/
│           ├── application.properties                   # 환경변수 주입 설정 포함 (portone.api-secret)
│
```                 
---

##📝 TODO
- [ ] 사용자 결제 내역 
