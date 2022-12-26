# Mutsa Final Project
> **멋사스네스(MutsaSNS) API 구현** <br>
> 멋쟁이사자처럼 백엔드 스쿨 2기 학생들의 학습 내용 정리를 위한 프로젝트

## 개발환경
- 에디터 : Intellij Ultimate
- 개발 툴 : SpringBoot 2.7.5
- 자바 : JAVA 11
- 빌드 : Gradle 6.8
- 서버 : AWS EC2
- 배포 : Docker
- 데이터베이스 : MySql 8.0
- 필수 라이브러리 : SpringBoot Web, MySQL, Spring Data JPA, Lombok, Spring Security

## 기능
- 회원가입
- Swagger
- AWS EC2에 Docker 배포
- Gitlab CI & Crontab CD
- 로그인 
- 포스트 작성, 수정, 삭제, 리스트

## Architecture(아키텍처)
![img.png](img.png)

## ERD
![img_1.png](img_1.png)

---

## 기능구현
### 회원가입&로그인 (url : `/users`)
**POST `/join`**
> 입력폼으로 받아온 정보를 DB에 저장   
> 테스트 2가지 (회원가입 성공,실패(중복id))    
> DB에 Request로 받아온 pw를 그대로 저장하지 않고, 암호화를 하여 저장    
> ㄴBCryptPasswordEncoder encode() 사용
* 입력폼
```json
{
  "userName": "user1",
  "password": "user1234"
}
```
* 리턴폼
```json
{
  "resultCode": "SUCCESS",
  "result": {
    "userId": 1,
    "userName": "user1"
  }
}
```
**POST `/login`**
> JWT를 사용하여 로그인 시 DB의 정보가 맞다면 토큰을 발급해줌    
> 테스트 3가지 (로그인 성공, 실패(id,pw오류))
* 입력폼
```json
{
	"userName" : "user1",
	"password" : "user1234"
}
```
* 리턴폼
```json
{
  "resultCode": "SUCCESS",
  "result": {
    "jwt": "eyJhbGciOiJIU",
  }
}
```
---
### 포스트 작성(url : `/posts`)
**POST `/posts`**
> 포스트 등록    
> 테스트 코드    
> - 포스트 등록 성공
> - 포스트 등록 실패(1) - 인증실패(JWT를 Bearer Token으로 보내지 않은 경우)
> - 포스트 등록 실패(2) - 인증실패(JWT가 유효하지 않은 경우)
* Request
```json
{
  "title": "title1",
  "body": "body1"
}
```
* Response
```json
{
	"resultCode":"SUCCESS",
	"result":{
		"message":"포스트 등록 완료",
		"postId":0
	}
}
```



