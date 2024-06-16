## 프로젝트 개요

**기존 SNS의 문제점인 과시소비, 자랑의 대척점에서 재미있게 지역절약정보를 공유하는 커뮤니티**

**기간** : 2024.01 ~ 2024.02 (6주)

**인원** : BE 3, FE 3

**역할(기여도)** : **BE(40%)**

### 🛠️ Stack 기술 스택

### **BackEnd**

`Spring Boot`  `Spring Security`  `Spring Data Jpa` `Spring Cloud Config`  `QueryDsl`   

---

### **Database**

`Azure MariaDB` `H2` `Redis`

---

### **ETC**

`AWS EC2` `AWS S3` `NginX`  `Docker` `Jenkins` `Gitlab CI/CD`

---

### 아키텍처

![](https://velog.velcdn.com/images/kgh2120/post/89216e25-6868-4bef-9704-d7dccdbef58b/image.png)

## 🖥️ 담당 기능

---

### Backend

- 채팅방 CRUD API 구현
- Spring Security와 Redis를 활용한 **세션기반 인증/인가** 구현
- STOMP를 활용한 채팅 구현
- 회원 탈퇴 기능 구현

## 🗯️ 주요 기능 및 성과

---

- Caffeine 캐싱을 활용한 **지역 조회 API 성능 개선**

![image](https://github.com/kgh2120/goumunity-backend/assets/76154390/124edea2-d682-474e-bf76-f44588f2fb0a)

- JdbcTemplate의 bulk 쿼리를 통한 **대량 삭제 쿼리 성능 개선**

![image](https://github.com/kgh2120/goumunity-backend/assets/76154390/39295b82-9ca9-408e-9143-8e61b2591a91)


- Spring Application Event를 활용해 각 **서비스의 의존성 하락**
- Redis를 활용해 세션 저장소를 구현해 세션 동기화 문제 해결

## 💡 배운 점

---

- 지속적으로 조회되는 기능에 대해서 캐싱을 통해 성능을 개선하는 방법에 대해서 배울 수 있었습니다. Local Cache와 Global Cache의 개념을 익혔고, 캐시 동기화 문제에 대해서 파악할 수 있었습니다.
- 성능 테스트를 하는 과정에서 분산 환경에서 발생할 수 있는 Network IO의 오버헤드에 대해서 배울 수 있었습니다. 이로 인한 성능 감소 문제에 대해서 인식할 수 있었습니다.
- 다중화 환경에서 세션을 이용하면 발생하는 세션 동기화 문제에 대해서 배울 수 있었습니다. 이 문제를 해결하기 위한 방법들에 대해서 배울 수 있었고, 공유 세션 저장소를 구현해 이를 해결할 수 있었습니다.
