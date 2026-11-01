# 장소 후기 기록 앱 (연습용)

팀 프로젝트에서 제 몫을 할 수준이 되기 위한 혼자 연습용 프로젝트.
미술관 등 방문한 장소를 검색해서 저장하고, 그 장소에 후기를 남기는 앱.

배포하지 않고 로컬에서만 실행한다.

## 기술 스택

- 프론트: React + TypeScript (Vite)
- 백엔드: Spring Boot (Java 17, Gradle)
- DB: PostgreSQL
- 장소 검색: 카카오 로컬 API

## 폴더 구조

```
.
├── backend/   # Spring Boot (Gradle)
│   └── src/main/java/com/practice/placereview/
│       ├── place/    # 장소 도메인 (엔티티, 리포지토리, 컨트롤러, 서비스)
│       ├── review/   # 후기 도메인
│       └── config/   # 공통 설정 (CORS, WebClient 등)
└── frontend/  # React + TypeScript (Vite)
    └── src/
        ├── api/     # 백엔드 호출 함수
        └── types/   # 공용 타입
```

## 데이터 모델 (초안)

- `place`: 카카오 장소 id(중복 방지용), 이름, 주소, 경도(x), 위도(y), 카테고리
- `review`: 장소 FK, 본문, 별점, 방문 날짜
- 관계: place 1 : review N

## 아키텍처 규칙

- 프론트는 카카오 API를 직접 호출하지 않는다.
  React → 백엔드(`/api/places/search?q=...`) → 카카오 순서로만 호출한다.
- 카카오 REST API 키는 백엔드에만 두고 깃에 올리지 않는다.
  `backend/src/main/resources/application-local.yml.example`을 복사해
  `application-local.yml`로 만들고 실제 키를 채운 뒤 `local` 프로필로 실행한다.
- 좌표는 경도(x), 위도(y) 순서에 주의한다.

## 로컬 실행

### 사전 준비

- PostgreSQL에 `places_review` 데이터베이스 생성
- 카카오 디벨로퍼스에서 REST API 키 발급

### 백엔드

```bash
cd backend
cp src/main/resources/application-local.yml.example src/main/resources/application-local.yml
# application-local.yml 에 DB 비밀번호, 카카오 REST API 키 채우기
./gradlew bootRun --args='--spring.profiles.active=local'
```

기본 포트: `8080`

### 프론트엔드

```bash
cd frontend
npm install
npm run dev
```

기본 포트: `5173` (dev 서버가 `/api` 요청을 `localhost:8080`으로 프록시)

## 작업 순서

1. place, review 테이블/엔티티 설계
2. 장소 검색 API 연동 (백엔드)
3. 검색 결과에서 장소 저장 → 후기 CRUD
4. React 화면 연결
5. (선택) 지도 표시

## 진행 상황

- 2026-11-01: 프로젝트 구조 초기 세팅 (backend Spring Boot 스켈레톤, frontend Vite+React+TS 스켈레톤)
