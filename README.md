# Bulletin-Board-Api
> Spring boot, Spring Data JPA 로 설계한 게시판 API입니다.

### 프로젝트 소개

서버-클라이언트의 역할을 Rest Api로 분리하기 위해 시작한 프로젝트입니다.<br>

### 프로젝트 기능

- **게시글 API -** CRUD 기능, 조회수, 검색, 정렬, 페이징
- **회원 API -** CRUD 기능
- **댓글 API -** CRUD 기능, 정렬, 페이징, 베스트 댓글
- **댓글 공감 API -** 공감, 취소, 중복 공감 제어, 셀프 공감 제어
  
- **접근 권한 인터셉터 -** 비로그인 사용자 제한, 비권한 객체 수정 삭제 제한
- **예외 처리 Advice -** 비지니스 예외 처리, 권한 예외 처리


#### 프레임워크 / 라이브러리
- Java 17
- SpringBoot 3.3.1
- Spring Data JPA

#### Build Tool
- Gradle 8.2.1

#### DataBase
- MySQL 8.0.34

### ToDo
- QueryDsl 도입
- Spring Security 도입
- 로그처리 AOP 설계
- 대댓글 기능 추가
- 첨부파일 기능 추가
- 쪽지보내기 기능 추가
- 프론트 도입

### 블로그
- [다양한 예외를 어떻게 처리할까?](https://velog.io/@jhg2819/Spring-%EB%8B%A4%EC%96%91%ED%95%9C-%EC%98%88%EC%99%B8%EB%A5%BC-%EC%96%B4%EB%96%BB%EA%B2%8C-%EC%B2%98%EB%A6%AC%ED%95%A0%EA%B9%8C)