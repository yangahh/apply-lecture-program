## 목차
1. [아키텍쳐 구조 설명](#아키텍쳐-구조-설명)
2. [요구사항 정의](#요구사항-정의)
3. [API 스펙 정의](#api-스펙-정의)
4. [ERD 설계](#erd-설계)
   <br>
   <br>
---

## 아키텍쳐 구조 설명

### 아키텍쳐 구성도
1. 도메인 단위로 먼저 패키징
2. 각 도메인별로 계층을 나누어 구성
```
  lecture (domain name)
  ├── domain
  │   ├── entity
  │   ├── repository
  │   └── service
  ├── application
  │   └── facade
  ├── interface
  │   └── api
  │       ├── controller
  │       ├── dto
  │       └── mapper
  └── infrastructure
    └── persistence
        └── repository

  ```
### 계층 별 상세 설명

- **domain**
    - 역할: 핵심 비즈니스 로직과 규칙을 담당하는 계층입니다. 애플리케이션의 변하지 않는 핵심 부분으로, 외부 계층에 의존하지 않으며, 외부 계층에서 이 계층에 접근합니다.
    - 하위 패키지
        - **entity**
            - 역할: 데이터베이스 테이블과 매핑되는 클래스이자 도메인 객체를 나타냅니다.
            - 예시: `Lecture`, `LectureRegistration`
        - **repository**
            - 역할: 엔티티의 데이터 접근을 추상화한 인터페이스가 이 계층에 포함됩니다. 구현체는 infrastructure 계층에 위치합니다.   
                   &nbsp; &nbsp; &nbsp; &nbsp;비즈니스 로직이 데이터 저장 방식에 의존하지 않도록 하기 위해(의존성 역전 원칙을 지키기 위해) 인터페이스를 사용합니다.
            - 예시: `LectureRepository`
        - **service**
            - 역할: 해당 도메인이 제공해야하는 비즈니스 로직을 처리합니다.
            - 예시: `LectureService`   
              <br>
- **application**
    - 역할: 도메인 계층과 인터페이스 계층 사이의 중재자 역할을 합니다.
    - 하위 패키지:
        - **facade**
            - 역할: 인터페이스에서 여러 도메인 서비스를 호출해야할 때, 도메인 서비스를 조합하여 제공하는 역할을 합니다. 이 패턴은 순환참조 문제를 줄여줄 수 있습니다.
            - 예시: `LectureRegistrationFacade`   
              <br>
- **interface/api**
    - 역할: interface 계층은 외부 시스템과의 연동을 담당합니다. 이 계층은 도메인 계층에 의존하며, 도메인 계층에서 이 계층에 접근합니다.
      api 패키지는 interface 중 REST API에 대한 요청을 처리하는 클래스가 위치합니다.
    - 하위 패키지:
        - **controller**
            - 역할: HTTP 요청을 받아 비즈니스 로직을 호출하고 결과를 반환합니다.
            - 예시: `LectureController`
        - **dto**
            - 역할: 클라이언트와 서버 간의 데이터 교환에 사용되는 객체들을 정의합니다.
            - 예시: `LectureRegistrationRequestDto`, `LectureResponseDto`
        - **mapper**
            - 역할: 해당 계층의 DTO와 도메인 엔티티 간의 변환 로직을 담당합니다.
            - 예시: `LectureDtoMapper`   
              <br>
- **infrastructure/persistence**
    - 역할: infrastructure 계층은 데이터베이스나 메시징 시스템과 같은 외부 시스템과의 통신 및 기술적 세부 사항을 처리합니다.
      persistence 패키지는 infrastructure 중 데이터베이스에 관련된 기능을 처리하는 클래스가 위치합니다.
    - 하위 패키지:
        - **repository**
            - 역할: 도메인 repository 인터페이스의 구체적인 구현체와 JPA, MyBatis 등의 ORM 인터페이스를 상속받는 인터페이스가 위치합니다.
            - 예시: `JpaLectureRepository`, `LectureRepositoryImpl`

---

## 요구사항 정의

### 특강 신청 API
- userId를 가지고 사용자가 선착순으로 제공되는 특강을 신청할 수 있는 API를 구현합니다.
- 특강의 정원은 30명으로 고정합니다. 정원이 초과되면 신청할 수 없습니다.
- 동일한 신청자는 동일한 강의에 대해서 한 번의 수강 신청만 성공할 수 있습니다.
    - userId가 같으면 동일한 신청자로 간주합니다.
    - 특강 ID(lectureId)가 같으면 동일한 강의로 간주합니다.


### 신청 가능한 특강 목록 조회 API
- 사용자가 특강을 신청하기 전에 신청 가능한 강의 목록을 조회할 수 있는 API를 구현합니다.
- 특강 신청일 기준으로 오늘 이후의 특강 목록을 조회할 수 있습니다.
- 결과에는 특강 ID, 특강명, 특강일, 강연자 이름, 정원 초과 여부에 대한 정보가 포함됩니다.
- 특정 날짜에 진행되는 특강 목록을 조회할 수 있습니다.
- 사용자에게 가장 임박한 특강을 놓치지 않고, 다가오는 일정도 쉽게 파악할 수 있도록 하기 위해 특강일 기준 오름차순 + 신청 종료 날짜기준 오름차순으로 정렬합니다.


### 특정 사용자가 신청한 특강 목록 조회 API
- 특정 userId를 가지고 해당 사용자가 신청한 특강 목록을 조회할 수 있는 API를 구현합니다.
- 결과에는 특강 ID, 특강명, 특강일, 강연자 이름, 신청일에 대한 정보가 포함됩니다.

---
## API 스펙 정의

### 특강 신청 API
- URL: `[POST] /lectures/{lectureId}/registrations`
- Request Body
    ```json
    {
      "userId": 1
    }
    ```
- Response
    ```json
    {
      "data": {
        "lectureId": 1,
        "userId": 1,
        "registrationId": 1,
        "lectureTitle": "Spring Boot",
        "lectureDatetime": "2024-12-30 10:00:00",
        "speakerName": "김땡땡",
        "registeredAt": "2024-12-25 10:00:00"
      }
    }
   ```

### 신청 가능한 특강 목록 조회 API
- URL: `[GET] /lectures?date={date}`
- Request Parameters
    - date: 특강일 (yyyy-MM-dd)
- Response
    ```json
    {
      "data": [
        {
          "lectureId": 1,
          "userId": 1,
          "lectureTitle": "Spring Boot",
          "lectureDatetime": "2024-12-30 10:00:00",
          "registrationStartDatetime": "2024-12-25 00:00:00",
          "registrationEndDatetime": "2024-12-27 00:00:00",
          "speakerName": "김땡땡",
          "isFull": false
        }
      ]
    }
    ```

### 특정 사용자가 신청한 특강 목록 조회 API
- URL: `[GET] /lectures/registrations?userId={userId}`
- Request Parameters
    - userId: 사용자 ID
- Response
    ```json
    {
      "data": [
        {
          "lectureId": 1,
          "userId": 1,
          "lectureTitle": "Spring Boot",
          "lectureDatetime": "2024-12-30 10:00:00",
          "speakerName": "김땡땡",
          "registeredAt": "2024-12-25 10:00:00"
        }
      ]
    }
    ```

---
## ERD 설계
<img width="931" alt="image" src="https://github.com/user-attachments/assets/6e538e77-8327-4d3a-b245-75c3cf5e4a24" />

### 테이블 설명
- lecture
  - 특강 정보를 저장하는 테이블입니다.
  - current_capacity는 현재 신청된 인원을 나타냅니다.   
<br>
- lecture_registration
  - 특강 신청 정보를 저장하는 테이블입니다.
  - user_id를 참조키로 user 테이블과 1:N 관계를 가집니다.
  - lecture_id를 참조키로 lecture 테이블과 1:N 관계를 가집니다.
  - user_id와 lecture_id를 복합 unique 제약 조건으로 설정하여 동일한 사용자가 동일한 강의에 대해 중복 신청을 방지합니다.

### 설계 시 고민했던 사항
- 처음 설계 시 동시성 제어와 조회 성능을 모두 고려하여, 특강 정보 중 정적 정보와 동적 정보(현재 신청 인원)를 분리하는 방안을 검토했습니다.   
  그러나 이번 프로젝트에서는 조회 요청 시에도 정원 초과 여부를 확인하기 위해 동적 정보가 필요하므로, 조회 시마다 결국 두 테이블을 조인해야 합니다.   
  또한, 비관적 락(Pessimistic Write)을 사용하더라도 조회 트랜잭션은 DB의 MVCC를 통해 이미 커밋된 스냅샷을 읽을 수 있으므로, 실제로 대기(Blocking)가 거의 발생하지 않는다고 판단했습니다.   
  이에 따라, 최종적으로 테이블을 분리하지 않고 lecture 테이블 안에 current_capacity 컬럼을 두어 관리하는 방식으로 설계했습니다.