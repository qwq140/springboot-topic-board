# TOPIC BOARD
## 개요
Topic Board를 위한 RESTful API를 제공합니다. 주제별로 게시판이 분리가 되어 있으며, 게시물을 작성하고, 댓글을 달 수 있습니다.

## 기술 스택
- Springboot 3.3.3
- Java 17
- Spring Data JPA
- Spring Security

## Database
### DDL
```sql
CREATE TABLE USER_TB (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(20) UNIQUE,
    password VARCHAR(60) NOT NULL,
    nickname VARCHAR(20) UNIQUE,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE USER_ROLE_TB (
    user_id BIGINT,
    role ENUM('ADMIN', 'USER') NOT NULL,
    PRIMARY KEY (user_id, role),
    FOREIGN KEY (user_id) REFERENCES USER_TB(id) ON DELETE CASCADE
);

CREATE TABLE BOARD_TB (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(60) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE POST_TB (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    author_id BIGINT NOT NULL,
    board_id BIGINT NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE COMMENT_TB (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    author_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (author_id) REFERENCES USER_TB(id) ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES POST_TB(id) ON DELETE CASCADE
);
```
### ERD
![erd_preview](./image/erd.png)

## API 개요
이 API는 다음과 같은 주요 기능을 제공합니다.
- 회원가입, 로그인, 토큰 재발급
- 게시판 생성, 조회, 수정, 삭제
- 게시글 작성, 조회, 수정, 삭제
- 댓글 작성, 조회, 수정, 삭제

## 엔드포인트 목록
### 1. 인증 API
#### 1.1 회원가입
- **URL**: `/api/auth/join`
- **Method**: `POST`
- **Request Body**:
    ```json
    {
      "username": "test1234",
      "password": "test@1234",
      "nickname": "테스터1"
    }
    ```
- **Response Body**:
    ```json
    {
        "code": 200,
        "message": "회원가입 완료",
        "data": {
            "accessToken": "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MjU4NzUwMTMsInN1YiI6InRvcGljLWJvYXJkIiwiaWQiOjEsInVzZXJuYW1lIjoidGVzdDEyMzQiLCJ0b2tlbi10eXBlIjoiQUNDRVNTX1RPS0VOIn0.HNYMGslCqW74WIRc9O561ITBF9xOzrnY2V2kM0LfQUOlAgmT2GvVXn6ByPYrPxeryQSu4e7wP0CJhRq9eQVT_Q",
            "refreshToken": "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MjY0NjkwMTMsInN1YiI6InRvcGljLWJvYXJkIiwiaWQiOjEsInVzZXJuYW1lIjoidGVzdDEyMzQiLCJ0b2tlbi10eXBlIjoiUkVGUkVTSF9UT0tFTiJ9.Cs4uMzczJcHaq2h3JBpW3TxMXXf-Hq27ppCthppB1WOOKTzuVVwrgs4kFDuLR51lmWh906_cAfUtY-dqnrYJ6A"
        }
    }
    ```
#### 1.2 로그인
- **URL**: `/api/auth/login`
- **Method**: `POST`
- **Request Body**:
    ```json
    {
        "username" : "test1234",
        "password" : "test@1234"
    }
    ```
- **Response Body**:
    ```json
    {
        "code": 200,
        "message": "로그인 완료",
        "data": {
            "accessToken": "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MjU4NzUyNTksInN1YiI6InRvcGljLWJvYXJkIiwiaWQiOjEsInVzZXJuYW1lIjoidGVzdDEyMzQiLCJ0b2tlbi10eXBlIjoiQUNDRVNTX1RPS0VOIn0.ZrAA7AEEya-G5QE0524fJo0P3U51iW6W_PIG3X9d25rPrBc9VSifqv3LtswoE9QLeNVdHFyrk7GTwE6WrG9APQ",
            "refreshToken": "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MjY0NjkyNTksInN1YiI6InRvcGljLWJvYXJkIiwiaWQiOjEsInVzZXJuYW1lIjoidGVzdDEyMzQiLCJ0b2tlbi10eXBlIjoiUkVGUkVTSF9UT0tFTiJ9.8QsAbcXsy5eXMDUuBHjDQfxGJvZOIbv34Af1hN3CGy4MPDhEkf6ikHVCFq-Fbj0OS6frknQXwc-wmJnIKGLZfQ"
        }
    }
    ```
#### 1.3 토큰 재발급
- **URL**: `/api/auth/reissue`
- **Method**: `POST`
- **Request Body**:
    ```json
    {
        "refreshToken" : "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MjY0NjkyNTksInN1YiI6InRvcGljLWJvYXJkIiwiaWQiOjEsInVzZXJuYW1lIjoidGVzdDEyMzQiLCJ0b2tlbi10eXBlIjoiUkVGUkVTSF9UT0tFTiJ9.8QsAbcXsy5eXMDUuBHjDQfxGJvZOIbv34Af1hN3CGy4MPDhEkf6ikHVCFq-Fbj0OS6frknQXwc-wmJnIKGLZfQ"
    }
    ```
- **Response Body**:
    ```json
    {
        "code": 200,
        "message": "토큰 재발급 완료",
        "data": {
            "accessToken": "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MjU4NzUzNzUsInN1YiI6InRvcGljLWJvYXJkIiwiaWQiOjEsInVzZXJuYW1lIjoidGVzdDEyMzQiLCJ0b2tlbi10eXBlIjoiQUNDRVNTX1RPS0VOIn0.3uWdewbHF3EDrILS_0Hkbqq7WzPsYpEphczAJkahta4XSwERxcamJhSi0CnisdSUmap2LRldLDhieSkuzxi8xQ",
            "refreshToken": "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MjY0NjkzNzUsInN1YiI6InRvcGljLWJvYXJkIiwiaWQiOjEsInVzZXJuYW1lIjoidGVzdDEyMzQiLCJ0b2tlbi10eXBlIjoiUkVGUkVTSF9UT0tFTiJ9.Ts0c9DPmCWXOt0lGnKaoc5Zmk8wIkkq66gzcp0jQI3E2Rk_5Xj0U_SJE2BPfpz6LzRWEsFgrBQtyK7UmfGHqiw"
        }
    }
    ```

### 2. 게시판 API
#### 2.1 게시판 생성
- **URL**: `/api/boards`
- **Method**: `POST`
- **Request Headers**:
  - `Content-Type: application/json`
  - `Authorization: Bearer {accessToken}`
- **Request Body**:
    ```json
    {
        "name" : "콘솔 게임",
        "description" : "콘솔 게임에 대한 정보를 공유하는 방입니다."
    }
    ```
- **Response Body**:
    ```json
    {
        "code": 200,
        "message": "게시판 등록 완료",
        "data": {
            "id": 1,
            "name": "콘솔 게임",
            "description": "콘솔 게임에 대한 정보를 공유하는 방입니다.",
            "createdDate": "2024-09-09 15:54:15"
        }
    }
    ```
- 권한 : 관리자(ADMIN) 권한 필요

#### 2.2 게시판 목록 조회
- **URL**: `/api/boards`
- **Method**: `GET`
- **Response Body**:
  ```json
  {
      "code": 200,
      "message": "게시판 목록 조회",
      "data": {
          "boards": [
              {
                  "id": 1,
                  "name": "콘솔 게임",
                  "description": "콘솔 게임에 대한 정보를 공유하는 방입니다."
              },
              {
                  "id": 2,
                  "name": "프로그래밍",
                  "description": "프그래밍 관련 정보를 교환하는 게시판입니다."
              }
          ]
      }
  }
  ```

#### 2.3 게시판 단건 조회
- **URL**: `/api/boards/{boardId}`
- **Method**: `GET`
- **Response Body**:
  ```json
  {
      "code": 200,
      "message": "게시판 단건 조회",
      "data": {
          "id": 1,
          "name": "프로그래밍",
          "description": "프로그래밍 관련 정보를 교환하는 게시판입니다.",
          "createdDate": "2024-09-09 16:37:21",
          "updatedDate": "2024-09-09 16:37:21"
      }
  }
  ```
  
#### 2.4 게시판 수정 
- **URL**: `/api/boards/{boardId}`
- **Method**: `PUT`
- **Request Headers**:
  - `Content-Type: application/json`
  - `Authorization: Bearer {accessToken}`
- **Request Body**:
  ```json
  {
    "name":"프로그래밍(수정)",
    "description" : "설명 수정"
  }
  ```
- **Response Body**:
  ```json
  {
    "code": 200,
    "message": "게시판 수정 완료",
    "data": {
      "id": 1,
      "name": "프로그래밍(수정)",
      "description": "설명 수정",
      "createdDate": "2024-09-09 16:37:21",
      "updatedDate": "2024-09-09 16:37:21"
    }
  }
  ```
- 권한 : 관리자(ADMIN) 권한 필요

#### 2.5 게시판 삭제
- **URL**: `/api/boards/{boardId}`
- **Method**: `DELETE`
- **Request Headers**:
  - `Authorization: Bearer {accessToken}`
- **Response Body**:
  ```json
  {
    "code": 200,
    "message": "게시판 삭제 완료",
    "data": null
  }
  ```
- 권한 : 관리자(ADMIN) 권한 필요

### 3.게시글 API
#### 3.1 게시글 생성
- **URL**: `/api/boards/{boardId}/posts`
- **Method**: `POST`
- **Request Header**:
  - `Content-Type: application/json`
  - `Authorization: Bearer {accessToken}`
- **Request Body**:
  ```json
  {
      "title" : "테스트 제목",
      "content" : "테스트 내용"
  }
  ```
- **Response Body**:
  ```json
  {
      "code": 1,
      "message": "게시글 작성 완료",
      "data": {
          "id": 1,
          "title": "테스트 제목",
          "content": "테스트 내용",
          "author": {
              "id": 2,
              "username": "test5678",
              "nickname": "테스터2"
          },
          "board": {
              "id": 1,
              "name": "콘솔 게임",
              "description": "콘솔 게임 관련 정보를 교환하는 게시판입니다."
          },
          "createdDate": "2024-09-09 17:49:09"
      }
  }
  ```
- 권한 : 인증된 사용자(USER)