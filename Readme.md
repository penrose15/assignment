### 요구사항

- [x] 로그인이 가능해야 한다. 
  - [x] 이때 JWT 기반으로 인증해야 한다.
  - [x] role 은 USER, ADMIN 2개
- [x] openAI와 챗봇 기능이 가능해야 한다. 
  - [x] 이때 유저 1개당 여러 스레드를 만들 수 있다. 
  - [x] 새로운 스레드는 가장 최근의 스레드에서 30분 이 지나면 새로 만들고 아니면 재사용
  - [x] 질문을 입력받고 생성된 답변을 응답한다. 
    - [ ] 파라미터 isStreaming, model -> model 만 사용
  - [x] 대화 목록 조회가 가능해야 한다.  
    - [ ] 스레드 단위로 조회 가능해야 하고, 관리자는 모든 스레드 대화 조회가 가능하다. -> 관리자 기능 미완
    - [x] 페이지네이션이 가능해야 한다. (오름차순/내림차순)
  - [x] 대화에 대한 피드백 작성이 가능해야 한다. 
    - [x] 피드백은 챗봇의 응답에 대해 달 수 있어야 한다. (유저 당 채팅에 1개씩만 가능)
  - [ ] 사용자의 활동 분석 가능(운영자만) 
    - [ ] 하루동안의 기록을 가져와야 한다.
    - [ ] 회원가입, 로그인, 대화 생성 수
    - [ ] 로그인이라면 최근 로그인 기록을 의미하는 건가??
    - [ ] 이 내용에 대해 csv 형태의 보고서를 생성해야 한다.(운영자만)
    - [ ] 하루동안의 보고서만 작성한다.

> 옆에 달린 1,2,3,4는 우선순위를 뜻함


### entity 관계

Users : Thread = 1 : N
Thread : chat = 1 : N
chat : feedback = 1 : N
feedback : user = N : 1

### API 정의서

* 채팅 생성

```
POST /chat
{
  "question" : "question"
}
```

* 채팅 조회
```
GET /chat?page=1&size=10&desc=true

```

* 피드백 생성(user)
```
POST /feedback
```

* 피드백 조회
```text
GET /feedback?page=1&size=10&is-desc=true&good-or-bad=true
```

* 쓰레드 삭제
```
DELETE /thread/{thread-id}
```

* 유저 회원가입
```text
POST /user/sign-up
```
* 유저 로그인
```text
POST /user/login
```