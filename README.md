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
├── .env.example   # 환경변수 템플릿 (복사해서 .env 로 사용)
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
- 카카오 REST API 키와 DB 비밀번호 같은 비밀 값은 코드에 쓰지 않고 환경변수(`.env`)로만 관리한다.
  `.env`는 깃에 올리지 않고, 템플릿인 `.env.example`만 올린다.
  카카오 키는 백엔드에서만 읽으며 프론트 번들에는 들어가지 않는다.
- 좌표는 경도(x), 위도(y) 순서에 주의한다.

## 로컬 실행

### 사전 준비

- Java 17, Node.js, PostgreSQL 설치
- PostgreSQL에 `place_review` 데이터베이스 생성
- 카카오 디벨로퍼스에서 REST API 키 발급

### 환경변수 설정 (.env)

백엔드와 프론트엔드가 프로젝트 루트의 `.env` 파일 하나를 함께 읽는다.

```bash
cp .env.example .env        # Windows: copy .env.example .env
```

`.env`를 열어 값을 채운다.

| 변수 | 설명 | 기본값 |
| --- | --- | --- |
| `DB_URL` | PostgreSQL JDBC 주소 | `jdbc:postgresql://localhost:5432/place_review` |
| `DB_USERNAME` | DB 사용자 | `postgres` |
| `DB_PASSWORD` | DB 비밀번호 | `postgres` |
| `KAKAO_REST_API_KEY` | 카카오 REST API 키 (**필수**) | 없음 |
| `SERVER_PORT` | 백엔드 포트 | `8080` |
| `JPA_DDL_AUTO` | 테이블 자동 생성 방식 (선택) | `update` |
| `JPA_SHOW_SQL` | SQL 로그 출력 여부 (선택) | `true` |
| `API_PROXY_TARGET` | 프론트 dev 서버가 `/api`를 넘길 백엔드 주소 (선택) | `http://localhost:SERVER_PORT` |

- 같은 이름의 OS 환경변수를 설정하면 `.env`보다 그 값이 우선한다.
- 카카오 키를 설정하지 않으면 장소 검색 시 설정 안내 오류가 난다.

### 백엔드

```bash
cd backend
./gradlew bootRun          # Windows: gradlew.bat bootRun
```

기본 포트: `8080`. `backend/`에서 실행해도 상위 폴더의 `.env`를 자동으로 읽는다.

IntelliJ에서 실행할 때도 작업 디렉터리가 `backend/` 또는 프로젝트 루트면 `.env`를 자동으로 읽는다.
`.env` 대신 실행 설정(Run Configuration)의 Environment variables에 `DB_PASSWORD=...;KAKAO_REST_API_KEY=...`를 넣어도 된다.

### 프론트엔드

```bash
cd frontend
npm install
npm run dev
```

기본 포트: `5173` (dev 서버가 `/api` 요청을 백엔드로 프록시. 대상은 `API_PROXY_TARGET` 또는 `SERVER_PORT`로 정해짐)

브라우저에서 `http://localhost:5173`으로 접속한다.

### (참고) 이전 방식: application-local.yml

`application-local.yml.example`을 복사해 `application-local.yml`로 만들고
`--spring.profiles.active=local`로 실행하는 방식도 여전히 동작한다.
이 파일의 값은 `.env`보다 우선하며, 마찬가지로 깃에 올라가지 않는다. 새로 시작한다면 `.env`를 쓰는 것을 권장한다.

## 작업 순서

1. place, review 테이블/엔티티 설계
2. 장소 검색 API 연동 (백엔드)
3. 검색 결과에서 장소 저장 → 후기 CRUD
4. React 화면 연결
5. (선택) 지도 표시

## 진행 상황

- 2026-11-01: 프로젝트 구조 초기 세팅 (backend Spring Boot 스켈레톤, frontend Vite+React+TS 스켈레톤)
- 2026-11-01: place/review 엔티티, 카카오 장소 검색 연동, 장소 저장 + 후기 CRUD API, React 화면(검색/저장/후기 CRUD) 전부 구현 및 브라우저 동작 확인 완료. 남은 건 (선택) 지도 표시뿐.
- 2026-11-02: DB 접속 정보, 카카오 키, 포트, 프론트 프록시 주소를 `.env`(환경변수)로 설정할 수 있게 변경하고 `.env.example` 추가
