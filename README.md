<div align="center">
    <h1>π’ Tech Stack </h1>
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

# Mutsa Final Project π λ©μ¬μ€λ€μ€(MustsaSNS)

## π νλ‘μ νΈ κ°μ
1οΈβ£ λ‘κ·ΈμΈ   
2οΈβ£ νμκ°μ   
3οΈβ£ κΈ μμ±/μμ /μ­μ /λ¦¬μ€νΈ    
4οΈβ£ λκΈ μμ±/μμ /μ­μ /λ¦¬μ€νΈ    
5οΈβ£ μ’μμ    
6οΈβ£ μλ    
7οΈβ£ λ§μ΄νΌλ    

1οΈβ£ ~ 7οΈβ£ κΈ°λ₯λ€μ μ¬μ©νμ¬ νμλ€λΌλ¦¬ μν΅νλ SNS μ νλ¦¬μΌμ΄μ

## π κ°λ°νκ²½
- μλν° : Intellij Ultimate
- κ°λ° ν΄ : SpringBoot 2.7.5
- μλ° : JAVA 11
- λΉλ : Gradle 6.8
- μλ² : AWS EC2
- λ°°ν¬ : Docker
- λ°μ΄ν°λ² μ΄μ€ : MySql 8.0
- νμ λΌμ΄λΈλ¬λ¦¬ : SpringBoot Web, MySQL, Spring Data JPA, Lombok, Spring Security

## π  κΈ°λ₯
- Swagger
- AWS EC2μ Docker λ°°ν¬
- Gitlab CI & Crontab CD

## π’ Swagger
http://ec2-52-79-78-160.ap-northeast-2.compute.amazonaws.com:8080/swagger-ui/

## π ERD
![](final_project_erd.png)

## π μν€νμ³
![](img.png)

## π EndPoint
> νμκ°μ
`POST /api/v1/users/join`    

> λ‘κ·ΈμΈ
`POST /api/v1/users/login`    

> ν¬μ€νΈ μ μ²΄ μ‘°ν
`GET /api/v1/posts`    

> ν¬μ€νΈ 1κ° μ‘°ν
`GET api/v1/posts/{postId}`    

> ν¬μ€νΈ λ±λ‘
`POST api/v1/posts`    

> ν¬μ€νΈ μμ 
`PUT api/v1/posts/{postId}`    

> ν¬μ€νΈ μ­μ 
`DELETE /api/v1/posts/{postId}`    

> λκΈ λ±λ‘
`POST /api/v1/posts/{postId}/comments`    

> λκΈ μμ 
`PUT /api/v1/posts/{postId}/comments/{id}`    

> λκΈ μ­μ 
`DELETE /api/v1/posts/{postId}/comments/{id}`    

> μ’μμ λλ₯΄κΈ°
`POST /api/v1/posts/{postId}/likes`    

> μ’μμ κ°μ
`GET /api/v1/posts/{postId}/likes`    

> λ°μ μλ μ‘°ν
`GET /api/v1/alarms`



## π κΈ°λ₯κ΅¬ν
* λͺ¨λ  κΈ°λ₯μ μλ΅κ°μ Responseλ‘ κ°μΈμ resultCodeμ resultλ‘ λλμ΄ λ³΄μ¬μ€λ€.
```json
{
  "resultCode": //... ,
  "result": {
        // ...
  }
}
```

---

### νμκ°μ, λ‘κ·ΈμΈ (url : `/api/v1/users`)
> **νμκ°μ `POST /join`**    
νμ κ°μ μ passwordλ encodingνμ¬ μνΈνλ μνλ‘ DBμ μ μ₯νλ€.
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
> **λ‘κ·ΈμΈ `POST /login`**    
Requestλ‘ λμ΄ μ¨ μ λ³΄μ DBμ μ μ₯λ μ λ³΄κ° κ°λ€λ©΄ jwt ν ν°μ λ°κΈν΄μ€

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

### ν¬μ€νΈ λ±λ‘/μμ /μ­μ /μ‘°ν (url : `/api/v1/posts`)
* ν¬μ€νΈ λ±λ‘ : νμλ§ κ°λ₯
* ν¬μ€νΈ μμ /μ­μ  : ν¬μ€νΈλ₯Ό λ±λ‘ν νμλ§ κ°λ₯
* ν¬μ€νΈ λ¦¬μ€νΈ/μμΈ μ‘°ν : νμ/λΉνμ λͺ¨λ κ°λ₯

> **ν¬μ€νΈ λ±λ‘ `POST ""`**

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
		"message":"ν¬μ€νΈ λ±λ‘ μλ£",
		"postId":0
	}
}
```

> **ν¬μ€νΈ μμ  `PUT /{postId}`**

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
		"message":"ν¬μ€νΈ μμ  μλ£",
		"postId":0
	}
}
```

> **ν¬μ€νΈ μ­μ  `DELETE /{postId}`**

* Response
```json
{
	"resultCode":"SUCCESS",
	"result":{
		"message":"ν¬μ€νΈ μμ  μλ£",
		"postId":0
	}
}
```

> **ν¬μ€νΈ μ μ²΄ μ‘°ν `GET ""`**    
Pageable ((μ΅μ μ, 20κ°μ© νμ))
* Response
```json
{
    "resultCode": "SUCCESS",
    "result": {
        "content": [
            {
                "id": 10,
                "title": "title1",
                "body": "body1",
                "userName": "userName1",
                "createdAt": "2022/12/22 10:43:25",
                "lastModifiedAt": "2022/12/22 10:43:25"
            },
            {
                "id": 9,
                "title": "title2",
                "body": "body2",
                "userName": "userName2",
                "createdAt": "2022/12/22 10:42:44",
                "lastModifiedAt": "2022/12/22 10:42:44"
            },
            // ...
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

> **ν¬μ€νΈ 1κ° μ‘°ν `GET /{postId}`**

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

---

### λκΈ λ±λ‘/μμ /μ­μ /μ‘°ν (url : `/api/v1/posts`)
* λκΈ μμ± : νμλ§ κ°λ₯
* λκΈ μμ /μ­μ  : λκΈμ μμ±ν νμλ§ κ°λ₯
* λκΈ μ‘°ν : νμ/λΉνμ λͺ¨λ κ°λ₯(token μμ΄ κ°λ₯)

> **λκΈ λ±λ‘ `POST /{postId}/comments`**

* Request
```json
{
	"comment" : "comment test4"
}
```
* Response
```json
{
	"resultCode": "SUCCESS",
	"result":{
		"id": 4,
		"comment": "comment test4",
		"userName": "test",
		"postId": 2,
		"createdAt": "2022-12-20T16:15:04.270741"
	}
}
```

> **λκΈ μμ  `PUT /{postId}/comments/{id}`**

* Request
```json
{
	"comment" : "modify comment"
}
```
* Response
```json
{
	"comment" : "modify comment"
}
```

> **λκΈ μ­μ  `DELETE /{postId}/comments/{id}`**

* Response
```json
{
	"resultCode": "SUCCESS",
    "result":{
                "message": "λκΈ μ­μ  μλ£",
                "id": 4
            }
}
```

---


### μ’μμ λλ₯΄κΈ°/μ’μμ μ‘°ν (url : `/api/v1/posts`)
* likeλ₯Ό ν λ² λλ₯Όλ λ§λ€ rowκ° 1κ°μ© μΆκ°λλ λ°©μ
* soft deleteλ₯Ό μ¬μ©νμ¬ rowκ° μ­μ λμ§ μκ³  deletedAtμ λ μ§μ μκ°μ΄ μ μ₯λλ λ°©μ

> **μ’μμ λλ₯΄κΈ° `POST /{postId}/likes`**    
likeλ₯Ό νλ² λ λλ₯΄λ©΄ μ’μμ μ·¨μ

* Request
```json
{
    "resultCode":"SUCCESS",
    "result": "μ’μμλ₯Ό λλ μ΅λλ€."
}
```

> μ’μμ μ‘°ν(κ°μ) `GET /{postId}/likes`

* Response
```json
{
    "resultCode":"SUCCESS",
    "result": 0
}
```

---


### μλ (url : `/api/v1/alarms`)
* Pageable (μ΅μ μ, 20κ°μ© νμ)

> **μλ λ¦¬μ€νΈ `GET ""`**    
Pageable μ€μ  λ΄μ©μ λ³΄μ΄μ§ μκ³ , contentμ κ°μΈμ Έ λ³΄μ¬μ£ΌκΈ°

* Response
```json
{
    "resultCode":"SUCCESS",
    "result": {
        "content":
        [
            {
                "id": 1,
                "alarmType": "NEW_LIKE_ON_POST",
                "fromUserId": 1,
                "targetId": 1,
                "text": "new like!",
                "createdAt": "2022-12-25T14:53:28.209+00:00"
            }
        ]
    }
}
```

---

### λ§μ΄ νΌλ (url : `/api/v1/posts`)
* λ‘κ·ΈμΈ λ μ μ λ§μ νΌλλͺ©λ‘μ νν°λ§νλ κΈ°λ₯
* Pageable

> **λ§μ΄ νΌλ μ‘°ν `GET /my`**

* Response
```json
{
  "resultCode": "SUCCESS",
  "result":{
    "content":
        [
            {
            "id": 4,
            "title": "test",
            "body": "body",
            "userName": "test",
            "createdAt": "2022-12-16T16:50:37.515952"
            }
        ],
	"pageable":
        {
            "sort":{"empty": true, "sorted": false, "unsorted": true }, "offset": 0,β¦},
            "last": true,
            "totalPages": 1,
            "totalElements": 1,
            "size": 20,
            "number": 0,
            "sort":{
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
    "numberOfElements": 1,
    "first": true,
    "empty": false
}
```


