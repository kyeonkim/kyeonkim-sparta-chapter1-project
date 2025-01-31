# Chapter1 Project - MSA 구성, Redis 캐싱

# 개요

MSA를 이용한 상품 주문 서비스 구현 프로젝트

# 기간

24.08.06 - 24.08.18(12일): 필수 기능

24.08.19 - 진행 중: 도전기능

# 구성원
개인 프로젝트

# 목표

- MSA를 이해하고 직접 설계해보기
- Eureka, Ribbon을 활용하여 분산 처리 시스템 구성해보기
- MSA에서 발생되는 병목 현상을 추적할 수 있도록 Zipkin 사용하기
- Redis를 활용한 캐싱 개념을 이해하고 필요한 곳에 활용하기

# 필수 기능

- ~~기본 API 구성~~
  - API 목록

    |API|des|
    |---|---|
    |`POST /products`| 상품 추가 API|
    |`GET /products` |상품 목록 조회 API|
    |`POST /order` |주문 추가 API|
    |`PUT /order/{orderId}`  |주문에 상품을 추가하는 API|
    |`GET /order/{orderId}`  |주문 단건 조회 API|
    |`GET /auth/signIn?user_id={string}`  |로그인 API|
  
  - ~~상품 서비스는 라운드로빈 형식으로 로드밸런싱 구성~~
    - 라운드로빈 형식으로 로드밸런싱을 구현해서 상품 서비스가 호출될 때마다 두 서비스를 반복하며 호출되게 구성
    - 상품 목록을 조회할 때마다 API 의 응답 헤더의 `Server-Port` 값이 `19093` , `19094` 로 변경
  - ~~분산추적 구현~~
    - 주문 서비스 와 상품 서비스 에 Zipkin 을 연동하고, 호출시 Zipkin 대시보드에 Duration 이 측정되는지 확인
  - ~~캐싱 기능 구현~~
    - 주문 조회 API 의 결과를 캐싱 처리하여 60초 동안은 DB 에서 불러오는 데이터가 아닌
      메모리에 캐싱된 데이터가 보여지도록 설정
  - ~~외부 요청 보호~~
    - Oauth2,JWT 기반으로 인증/인가를 구성하여 인가 없이 상품 서비스, 주문 서비스를 호출할 때, 
      401 HTTP Status Code를 응답하도록 설정
  - ~~캐시 활용~~
    - 상품 추가 API  를 호출 할 경우 상품 조회 API 의 응답 데이터 캐시가 갱신되도록 구현
    - 상품 추가 후 상품 조회 API 호출 시 데이터가 변경 되는지 확인
  - ~~로컬과 서버의 환경을 분리~~
    - 로컬에서는 localhost:3306 으로 DB에 접근하고, 서버에 배포시엔 RDS 주소로 접근하게 구성하도록 환경을 분리(환경은 dev, prod 프로필로)

# 도전 기능

-  Layered Architecture 에 따라 고수준 모듈을 보호하는 구성으로 프로젝트를 구현
   - 실무 예시: [우아한 형제들 - 주니어 개발자의 클린 아키텍처 맛보기](https://techblog.woowahan.com/2647/)


- ~~DB를 이용한 회원가입으로 서비스 구현~~
  - MSA 환경에서 Oauth2 인증/인가 구현
    1. 회원가입 기능을 구현 = POST auth/signUp
    2. Gateway 서비스의 Filter 로직을 수정하여 Oauth2 규칙에 따라 헤더의 JWT 토큰이 회원가입 한 유저의 토큰인지 검증하는 로직을 구현
    3. 상품 서비스, 주문 서비스 를 호출해보며 정상적으로 인가 처리가 되었는지 확인
  - 실무 예시: [카카오 광고 플랫폼 - Gateway 를 이용한 인증/인가 처리 사례 (78슬라이드 ~ )](https://www.slideshare.net/slideshow/msa-api-gateway/113145634#78)