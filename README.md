
# 🧠 Re_Tap Backend

A secure and scalable backend for Re_Tap — a goal-based routine tracking app designed to help users build habits and stay motivated.  
This backend handles OAuth login, push notifications, and zero-downtime deployment, enabling a smooth and reliable experience.

---

## 🧠 About

Re_Tap lets users set personal goals, lock them for accountability, and receive feedback over time.  
The backend supports user authentication, notification delivery, and automation — all built with production-grade tools and deployment practices.

---

## ⚙️ Key Features

- Google & Kakao OAuth 2.0 login with JWT issuance and unified signup/login flow
- Access + Refresh Token (RTR) architecture for continuous authentication
- Refresh Token stored in RDB for seamless renewal
- Custom JWT filter, EntryPoint, and AccessDeniedHandler with standardized error response
- Global exception handling via `@ControllerAdvice` and `ErrorCode` enum
- Firebase Admin SDK-based FCM push notification system
- Scheduled goal notifications using Spring `@Scheduled`
- Service-layer unit tests with JUnit 5 and Mockito
- CI/CD pipeline: GitHub Actions + Docker + Nginx Blue/Green deployment
- Docker Compose on AWS EC2 + Nginx reverse proxy-based routing

---

## 🛠 Tech Stack

- **Spring Boot** / **Spring Security**
- **MariaDB** / **JPA** / **QueryDSL**
- **Firebase Admin SDK**
- **GitHub Actions**, **Docker**, **Nginx**
- **JUnit5**, **Mockito**

---

## 🚀 Getting Started

```bash
# Clone the project
git clone https://github.com/your-id/re_tap_backend.git

# Build
./gradlew build

# Run with Docker Compose
docker-compose up -d
```

---

# 🧠 Re_Tap 백엔드

사용자의 목표 설정과 루틴 실행을 돕는 앱 Re_Tap의 백엔드 시스템입니다.  
OAuth 로그인, 푸시 알림, 무중단 배포까지 고려하여 **안정성과 실용성을 모두 갖춘 인프라**를 구성했습니다.

---

## 🧠 소개

Re_Tap은 사용자가 개인 목표를 생성하고, 루틴을 잠금 설정하며, 정기적인 피드백을 통해 습관을 만들어가는 루틴 관리 서비스입니다.  
이 백엔드는 인증, 알림, 자동화 스케줄링, 운영 배포를 모두 담당합니다.

---

## ⚙️ 주요 기능

- 구글 / 카카오 OAuth 2.0 로그인 → JWT 발급 및 통합 처리
- Access + Refresh Token(RTR) 구조 설계 및 RDB 저장
- JWT 필터, 인증 실패/권한 실패 응답 표준화
- 전역 예외 처리: GlobalExceptionHandler + ErrorCode Enum
- Firebase FCM 기반 푸시 알림 발송 시스템
- Spring `@Scheduled` 기반 목표 알림 예약 스케줄러
- JUnit5 + Mockito 단위 테스트 작성
- GitHub Actions + Docker + Nginx 블루그린 무중단 배포 구성
- EC2 기반 Docker Compose 환경 + 리버스 프록시 설정

---

## 🛠 기술 스택

- **Spring Boot**, **Spring Security**
- **MariaDB**, **JPA**, **QueryDSL**
- **Firebase Admin SDK**
- **GitHub Actions**, **Docker**, **Nginx**
- **JUnit5**, **Mockito**

---

## 🚀 시작 방법

```bash
# 프로젝트 클론
git clone https://github.com/your-id/re_tap_backend.git

# 빌드
./gradlew build

# 도커로 실행
docker-compose up -d
```
