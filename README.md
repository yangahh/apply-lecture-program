## 목차
1. [아키텍쳐 구조 설명](#아키텍쳐-구조-설명)
2. 요구사항 정의
3. ERD 설계
4. API 스펙 정의
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