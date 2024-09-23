
# ✉️ J에게
업체의 주문에 대해 물류 관리 및 배송 시스템을 관리하기 위한 Micro Service Architecture 기반의 팀프로젝트 입니다.

## 👥 개발 인원
* 👸이수정 - [Github 링크](https://github.com/Krystal-13)
* 🧑‍💻지민철 - [Github 링크](https://github.com/jiminchur)
* 👨‍💻이상우 - [Github 링크](https://github.com/lswoo0705)

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

## 프로젝트 기능
### 예시
> * 예시 설명
> * 예시 설명

## 적용 기술
### 예시
> * 예시 설명
> * 예시 설명

## 테스트 방법

## 기타 명세서 및 설계서
* 요구사항 명세서 - [Wiki 보기](https://github.com/ForJJJ/DeliverySystem/wiki/%EC%9A%94%EA%B5%AC%EC%82%AC%ED%95%AD-%EB%AA%85%EC%84%B8%EC%84%9C)
* Github Commit Convention - [Wiki 보기](https://github.com/ForJJJ/DeliverySystem/wiki/Commit-Convention)
* Github PR Convention - [Wiki 보기](https://github.com/ForJJJ/DeliverySystem/wiki/Pull-Request-Convention)
* Our Rules - [Wiki 보기](https://github.com/ForJJJ/DeliverySystem/wiki/OurRules)
* ERD - [Wiki 보기]([https://github.com/ForJJJ/DeliverySystem.wiki.git](https://github.com/ForJJJ/DeliverySystem/wiki/ERD))
* 인프라 설계도 - [Wiki 보기](https://github.com/ForJJJ/DeliverySystem/wiki/%EC%9D%B8%ED%94%84%EB%9D%BC-%EC%84%A4%EA%B3%84%EB%8F%84)
* Todo - [Github Projects 보기](https://github.com/orgs/ForJJJ/projects/1/views/1)
* API 명세서 - [Notion 보기](https://www.notion.so/3debd825cc8b4e01bdf6a465b9c95ede?v=b17aa4eb7e3f4a3a9a530042fe361c15)
* 테이블 설계서 - [Notion 보기](https://www.notion.so/ac46c4d8638d4448ab1fa1088fa030db)
