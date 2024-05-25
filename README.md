# f1v3-log

Spring Boot + Vue.js를 사용하여 나의 기록을 남기기



> SSR(Server Side Rendering) 방식의 개발만 진행했기 때문에  
> **SPA(Single Page Application)** 방식의 개발을 진행 할 예정입니다.
>
> 즉, RestController를 사용하여 JSON 형태로 데이터를 주고 받는 형식으로 진행

### Spring REST Docs 도입

- 운영 코드에 영향
- 테스트 케이스 작성에 따라 문서를 생성
    - 코드 수정에 따른 문서를 수정해야하는 문제점 해결

## Password 암호화

1. 해시
2. 해시 방식
    1. SHA-1
    2. SHA-256
    3. MD5
    4. 왜 이러한 알고리즘으로 암호화 하면 안될까?
3. BCrypt, SCrypt, Argon2
4. Salt 값을 사용하는 이유!

> Spring Security -> SCrypt를 사용해서 비밀번호 암호화를 진행해보자.

## API

### 게시글

- POST /posts
- GET /posts/{postId}

### 댓글

- POST /posts/{postId}/comments

```json
{
  "author": "seungjo",
  "content": "hello!"
}
```

- DELETE /comments/{commentId}
- PATCH /comments/{commentId}