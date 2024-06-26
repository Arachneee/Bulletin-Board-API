# Bulletin-Board-Api
> Spring boot, Spring Data JPA, Vue.js 로 CSR 방식으로 설계한 게시판입니다.

### 프로젝트 소개

기존 SSR 방식에서 Rest Api로 CSR 방식으로 변경한 프로젝트입니다.<br>

### 프로젝트 기능

- **게시글 API -** CRUD 기능, 조회수, 검색, 정렬, 페이징, 이미지 파일 첨부, 추천
- **회원 API -** CRUD 기능
- **댓글 API -** CRUD 기능, 정렬, 페이징, 베스트 댓글
- **댓글 공감 API -** 공감, 취소, 중복 공감 제어, 셀프 공감 제어
  
- **접근 권한 인터셉터 -** 비로그인 사용자 제한, 비권한 객체 수정 삭제 제한
- -> Spring Security 로 변경
- **예외 처리 Advice -** 비지니스 예외 처리, 권한 예외 처리


#### 프레임워크 / 라이브러리
##### 백엔드
- Java 17
- SpringBoot 3.3.1
- Spring Data JPA
- QueryDsl
- Spring Security 6.1.3

##### 프론트엔드
- Vue.js 3.3.4

#### Build Tool
- Gradle 8.2.1

#### DataBase
- MySQL 8.0.34

### DB 설계
![erd](https://github.com/Arachneee/Bulletin-Board-API/assets/66822642/9241c880-9147-48e5-91d4-41810aaf67b1)

### ToDo
- ~~QueryDsl 도입~~
- ~~대댓글 기능 추가~~
- ~~첨부파일 기능 추가~~
- ~~권한 분류~~
- ~~Spring Security 도입~~
- JWT 인증 추가
- 로그처리 AOP 설계
- MyPage, ID/PW 찾기
- 쪽지보내기 기능 추가
- Spring Rest Doc 도입
