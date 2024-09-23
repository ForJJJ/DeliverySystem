
# ✉️ J에게
업체의 주문에 대해 물류 관리 및 배송 시스템을 관리하기 위한 Micro Service Architecture 기반의 팀프로젝트 입니다.

## How To Start
1. 우선 프로젝트 Git Clone을 받아준다.
```
git clone https://github.com/ForJJJ/DeliverySystem.git
```
2. com.forj.delivery-system 폴더로 들어가서 docker compose up 을 해준다.
   * `Postgresql ID / PW` : 원하는 걸로 설정하기
   * `Redis` 원하는 걸로 설정하기
   * `RabbitMQ ID /PW` : 원하는 걸로 설정하기 (기본값 : guest / guest)
```
cd com.forj.delivery-system
docker compose up -d
```
3. Gradle build 하기
4. application.yml 설정하기
5. 프로젝트 실행 순서
```
Servcr -> Gateway -> Hub -> Company -> 그외 순서 상관없음...
```

## 👥 개발 인원
* 👸이수정 - [Github 링크](https://github.com/Krystal-13)
* 🧑‍💻지민철 - [Github 링크](https://github.com/jiminchur)
* 👨‍💻이상우 - [Github 링크](https://github.com/lswoo0705)

## 프로젝트 주요 기능
### 허브 간 이동정보관리
> * 허브가 생성되면 허브 간 이동 정보가 동시에 생성됩니다. 이를 통해 허브 간의 이동 경로와 소요 시간 등의 정보를 사전에 준비하여, 실시간으로 요청이 들어왔을 때 빠르게 응답할 수 있습니다.
> * 사전 생성된 허브 간 이동 정보는 다른 API에서 효율적으로 조회할 수 있으며, 물류 경로 최적화를 제공합니다.
> * 최적 삽입 위치 계산 플로우
> 1. 기존 허브 경로 정보 조회
> 2. 각 허브 사이의 이동 거리 및 시간 계산
>   * 기존 허브의 출발 지점과 도착 지점 사이에 새로운 허브를 삽입했을 때, 추가로 발생하는 이동 시간과 거리를 계산
>   * 예: 허브 A -> 허브 B 사이에 새로운 허브 C가 삽입될 경우, A -> C, C -> B의 거리와 시간을 계산하여 기존 경로(A -> B)와 비교
> 3. 최적 위치 찾기
>   * 추가 비용(newDistance - currentDistance)이 가장 적은 위치를 선택
> 4. 최적 위치에 허브 삽입
>   * 예: 기존 경로 A -> B가 있다면, A -> C -> B로 변경하고, 기존 A -> B 경로를 삭제

### 주문 시나리오
> * 주문 생성 : 주문이 생성이 되면 Product쪽 forj.product queue로 주문 정보를 전달합니다.
> * 상품 수량 검증 : forj.product에서 받은 정보중 상품ID와 상품 수량에대한 검증이 진행됩니다. 통과가 되면 forj.delivery로 주문정보가 전달됩니다. 수량이 재고보다 초과가 되면 forj.error.product로 rollback이 일어납니다.
> * 배송 생성 : 상푸 수량검증이 끝나면 forj.delivery에서 받은 주문 정보로 배송이 생성이 됩니다.

<details>
    <summary>참고 이미지</summary>

<img width="607" alt="스크린샷 2024-09-23 오후 3 03 12" src="https://github.com/user-attachments/assets/ea997cb0-e76b-4524-b80a-62fb8892736d">

</details>

### 배송 시나리오
> * 배달 기사님 배송 선택 : 해당 허브의 기사님이 배송건을 잡으면 주문 쪽으로는 forj.complete.order로 해당 주문ID, 배송ID를 전달하고 주문 상태 COMPLETE로 변경, 배송 기록 쪽에도 forj.delivery-history에 배송정보 전달
> * 배송 기록 생성 : forj.delivery-history에서 받은 배송정보로 허브간 이동정보DB를 조회해서 시퀀스에 따라 테이블에 기록하기
> * 도착 알림 : 배송기사님이 중간지점 도착 알림 API를 날리게 되면 진행중인 순서는 상태값이 COMPLETE로 변경, 걸린 실제 시간 기록, 다음 배송순서는 READY -> PROGRESS로 변경
> * 최종 목적지 도착 : 최종 목적지 도착시 도착 API를 날리게 되면 마지막 순서까지 상태값이 COMPLETE로 변경이 되고 forj.complete.delivery쪽으로 해당 배송ID 전달
> * 배송 완료 : forj.complete.delivery쪽에서 받은 배송ID에 상태값을 COMPLETE로 변경

<details>
    <summary>참고 이미지</summary>

<img width="781" alt="스크린샷 2024-09-23 오후 3 03 35" src="https://github.com/user-attachments/assets/348aa58e-3d23-4d10-b221-027c787db0f5">

</details>

## 적용 기술

### RabbitMQ
> * 주문이 동시에 들어올 가정하에 비동기 처리로 주문을 효율적으로 처리합니다.
> * 주문 생성 시 forj.product queue를 통해 주문 정보를 전달하여 시스템의 응답성을 높입니다.
> * 상품 수량 검증 및 배송 생성 과정에서 발생할 수 있는 지연을 최소화하고, 각 단계의 처리를 독립적으로 수행할 수 있도록 지원합니다.
> * 오류 발생 시 forj.error.product로의 롤백을 통해 신뢰성을 보장하고, 전반적인 주문 처리 흐름을 안정적으로 유지합니다.

### Redis
> * 허브 간 이동 정보와 같은 빈번한 조회 데이터를 Redis에 짧은 시간 단위로 캐싱하여, 외부 API 호출 빈도를 줄이고 조회 성능을 향상시켰습니다.
> * 이를 통해 시스템 부하를 줄이고, 응답 속도를 최적화했습니다.

### Spring Security
> * 로그인한 회원은 서버로부터 JWT를 발급받습니다.
> * 회원은 발급받은 JWT를 사용해 각 권한에 맞는 API에 접근할 수 있습니다.
> * API Gateway에서 JWT 검증을 진행하고, 인증된 사용자는 각 API에서 권한을 확인받습니다.

### OPEN API - OpenWeather API 연동을 통한 날씨 예보 정보 제공
> * 각 허브는 위치에 대한 위도/경도 데이터를 가지고 있기 때문에 담당하는 배송 담당자는 매일 아침 그 날의 날씨 정보를 공유받습니다.
> * 허브 지역의 위치 좌표를 OpenWeather api에 전달하여 해당 지역의 실시간 날씨 정보를 가져옵니다.
> * 이 정보를 활용해 사용자가 원하는 날씨 데이터를 실시간으로 확인할 수 있습니다.

### OPEN API - Gemini AI API 연동을 통한 데이터 요청 및 처리
> * Gemini AI에 데이터와 요청 정보를 전달하여 원하는 형식의 데이터를 받습니다.
> * 사용자는 위에 요청한 날씨 정보들과 원하는 데이터(배송정보 등)를 Gemini에게 넘겨주어 원하는 형식의 정리된 text 메시지 데이터를 받습니다.

### OPEN API - Slack API 연동을 통한 Bot 메시지 전달
> * 회원가입 시 등록한 Slack 고유 ID값을 사용해 관리자는 Slack Bot으로 메시지를 전달할 수 있습니다.
> * 해당 사용자가 업체배송담당자로 특정 허브에 소속되어있다면 그 허브의 위치의 좌표값과 배송 내역들을 조회하여 날씨 정보(2. OpenWeather)와 배송 정보를 Gemini API(3. Gemini API)에 전달해 받은 text 메시지 데이터를 Slack Bot으로 전송합니다.
> * 해당 사용자가 허브배송담당자라면 해당 허브의 배송 정보를 조회해 Gemini API(3. Gemini API)에 전달하고, 반환된 메시지를 Slack Bot으로 전송합니다.
> * Gemini에 요청한 내용의 data와 반환받은 data는 db에 저장되어 조회가 가능합니다.

### API Test
> * Post Man
> * HTTP Test

## 테스트 방법

## 기타 명세서 및 설계서
* 요구사항 명세서 - [Wiki 보기](https://github.com/ForJJJ/DeliverySystem/wiki/%EC%9A%94%EA%B5%AC%EC%82%AC%ED%95%AD-%EB%AA%85%EC%84%B8%EC%84%9C)
* Github Commit Convention - [Wiki 보기](https://github.com/ForJJJ/DeliverySystem/wiki/Commit-Convention)
* Github PR Convention - [Wiki 보기](https://github.com/ForJJJ/DeliverySystem/wiki/Pull-Request-Convention)
* Our Rules - [Wiki 보기](https://github.com/ForJJJ/DeliverySystem/wiki/OurRules)
* ERD - [Wiki 보기](https://github.com/ForJJJ/DeliverySystem/wiki/ERD)
* 인프라 설계도 - [Wiki 보기](https://github.com/ForJJJ/DeliverySystem/wiki/%EC%9D%B8%ED%94%84%EB%9D%BC-%EC%84%A4%EA%B3%84%EB%8F%84)
* Todo - [Github Projects 보기](https://github.com/orgs/ForJJJ/projects/1/views/1)
* API 명세서 - [Notion 보기](https://www.notion.so/3debd825cc8b4e01bdf6a465b9c95ede?v=b17aa4eb7e3f4a3a9a530042fe361c15)
* 테이블 설계서 - [Notion 보기](https://www.notion.so/ac46c4d8638d4448ab1fa1088fa030db)
