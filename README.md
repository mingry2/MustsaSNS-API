![header](https://capsule-render.vercel.app/api?type=wave&color=auto&height=300&section=header&text=mutsaSNS&%20render&fontSize=90)

<div align="center">
 <h1>📢 Tech Stack </h1>
</div>
<div align="center">
	<img src="https://img.shields.io/badge/Java-007396?style=flat&logo=Java&logoColor=white" />
  <img src="https://img.shields.io/badge/GitLab-FC6D26?style=flat&logo=GitLab&logoColor=white" /><br>
	<img src="https://img.shields.io/badge/spring-6DB33F?style=flat&logo=spring&logoColor=white" />
	<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat&logo=springboot&logoColor=white" />
  <img src="https://img.shields.io/badge/SpringSecurity-6DB33F?style=flat&logo=springsecurity&logoColor=white" /><br>
  <img src="https://img.shields.io/badge/Docker-2496ED?style=flat&logo=Docker&logoColor=white" />
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=MySQL&logoColor=white" /><br>
  <img src="https://img.shields.io/badge/AmazonAWS-232F3E?style=flat&logo=AmazonAWS&logoColor=white" />
  <img src="https://img.shields.io/badge/JUnit5-25A162?style=flat&logo=JUnit5&logoColor=white" />
</div>
<br>

# 💻Mutsa Final Project
**멋사스네스(MutsaSNS) API 구현** <br>
멋쟁이사자처럼 백엔드 스쿨 2기 학생들의 학습 내용 정리를 위한 프로젝트   

1️⃣ 로그인   
2️⃣ 회원가입   
3️⃣ 글작성/수정/삭제   
4️⃣ 글리스트

## 🛠 개발환경
- 에디터 : Intellij Ultimate
- 개발 툴 : SpringBoot 2.7.5
- 자바 : JAVA 11
- 빌드 : Gradle 6.8
- 서버 : AWS EC2
- 배포 : Docker
- 데이터베이스 : MySql 8.0
- 필수 라이브러리 : SpringBoot Web, MySQL, Spring Data JPA, Lombok, Spring Security

## ⚒ 기능
- 회원가입
- Swagger
- AWS EC2에 Docker 배포
- Gitlab CI & Crontab CD
- 로그인 
- 포스트 작성, 수정, 삭제, 리스트

## 💡 접속 가능 링크
http://ec2-13-209-2-255.ap-northeast-2.compute.amazonaws.com:8080/api/v1/posts

## 💡 Swagger
http://ec2-13-209-2-255.ap-northeast-2.compute.amazonaws.com:8080/swagger-ui/

## 👉 EndPoints
> 회원가입
* POST /api/v1/users/join    
📚 http://ec2-13-209-2-255.ap-northeast-2.compute.amazonaws.com:8080/api/v1/users/join
> 로그인
* POST /api/v1/users/login    
📚 http://ec2-13-209-2-255.ap-northeast-2.compute.amazonaws.com:8080/api/v1/users/login
> 조회
* GET /api/v1/posts
📚 http://ec2-13-209-2-255.ap-northeast-2.compute.amazonaws.com:8080/api/v1/posts?pageNumber=1&pageSize=20
> 포스트 1개 조회
* GET api/v1/posts/{postId}
📚 http://ec2-13-209-2-255.ap-northeast-2.compute.amazonaws.com:8080/api/v1/posts/1
> 포스트 등록
* POST api/v1/posts
📚 http://ec2-13-209-2-255.ap-northeast-2.compute.amazonaws.com:8080/api/v1/posts/1
> 포스트 수정
* PUT api/v1/posts/{postId}
📚 http://ec2-13-209-2-255.ap-northeast-2.compute.amazonaws.com:8080/api/v1/posts/1
> 포스트 삭제
* DELETE /api/v1/posts/{postId}
📚 http://ec2-13-209-2-255.ap-northeast-2.compute.amazonaws.com:8080/api/v1/posts/1

## Architecture(아키텍처)
![img.png](img.png)

## ERD
![img_1.png](img_1.png)

## 😎 기능구현
### 회원가입&로그인 (url : `api/v1/users`)
**🌈 POST `/join`**
> 입력폼으로 받아온 정보를 DB에 저장   
> 테스트 2가지 (회원가입 성공,실패(중복id))    
> DB에 Request로 받아온 pw를 그대로 저장하지 않고, 암호화를 하여 저장    
> ㄴBCryptPasswordEncoder encode() 사용
* Request
```json
{
  "userName": "user1",
  "password": "user1234"
}
```
* Response
```json
{
  "resultCode": "SUCCESS",
  "result": {
    "userId": 1,
    "userName": "user1"
  }
}
```
**🌈 POST `/login`**
> JWT를 사용하여 로그인 시 DB의 정보가 맞다면 토큰을 발급해줌    
> 테스트 3가지 (로그인 성공, 실패(id,pw오류))
* Request
```json
{
	"userName" : "user1",
	"password" : "user1234"
}
```
* Response
```json
{
  "resultCode": "SUCCESS",
  "result": {
    "jwt": "eyJhbGciOiJIU",
  }
}
```
---
### 포스트 등록,조회,수정,삭제(url : `api/v1/posts`)

**🌈 POST `""`**
> 포스트 등록    
> 테스트 코드(controller)    
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
**🌈 GET `""`**
> 포스트 전체 조회(20개표시)
```json
{
    "resultCode": "SUCCESS",
    "result": {
        "content": [
            {
                "id": 10,
                "title": "글이 들어온다아아아",
                "body": "글들어온다아앙",
                "userName": "손흥민",
                "createdAt": "2022/12/22 10:43:25",
                "lastModifiedAt": "2022/12/22 10:43:25"
            },
            {
                "id": 9,
                "title": "`12`1",
                "body": "2`12`12`12",
                "userName": "손흥민",
                "createdAt": "2022/12/22 10:42:44",
                "lastModifiedAt": "2022/12/22 10:42:44"
            },
            {
                "id": 8,
                "title": "오늘은 더 추워",
                "body": "집이 최고",
                "userName": "손흥민",
                "createdAt": "2022/12/22 10:08:10",
                "lastModifiedAt": "2022/12/22 10:08:10"
            },
            {
                "id": 6,
                "title": "오늘 춥네요",
                "body": "눈이 엄청왔어요",
                "userName": "string",
                "createdAt": "2022/12/21 13:29:02",
                "lastModifiedAt": "2022/12/21 13:29:02"
            }
        ],
        "pageable": "INSTANCE",
        "last": true,
        "totalPages": 1,
        "totalElements": 4,
        "size": 4,
        "number": 0,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "first": true,
        "numberOfElements": 4,
        "empty": false
    }
}
```
**🌈 GET `/{postId}`**
> 포스트 1개 조회
* Response
```json
{
	"resultCode":"SUCCESS",
	"result":{
		"id" : 1,
		"title" : "title1",
		"body" : "body",
		"userName" : "user1",
		"createdAt" : yyyy-mm-dd hh:mm:ss,
		"lastModifiedAt" : yyyy-mm-dd hh:mm:ss
	}
}
```
**🌈 PUT `/{postId}`**
> 포스트 수정
* Response
```json
{
	"title" : "modified title",
	"body" : "modified body"
}
```
* Request
```json
{
	"resultCode":"SUCCESS",
	"result":{
		"message":"포스트 수정 완료",
		"postId":0
	}
}
```
** DELETE `/{postId}`
* Response
```json
{
	"resultCode":"SUCCESS",
	"result":{
		"message":"포스트 수정 완료",
		"postId":0
	}
}
```




