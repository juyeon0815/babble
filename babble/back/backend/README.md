# Dependencies
[Back-end]
MySQL
Java 8+
Gradle 6.7+
Eclipse or IntelliJ

[Front-end]
Node 14+
VS Studio

# mysql 접근
url: jdbc:mysql://localhost:3306
username: ssafy
password: ssafy

# 데이터베이스 생성
```sql
create database IF NOT EXISTS `babble` collate utf8mb4_general_ci;
```

# Front-end Setup
```bash
cd src/main/resources 
npm install
```

# Front-end Webpack Dev. 모드 실행
```bash
cd src/main/resources 
npm run serve
```

# Front-end 빌드
```bash
cd src/main/resources 
npm run build
```

# Back-end + Front-end 전체 빌드 (프로젝트 루트 디렉토리에서 수행)
```bash
gradle clean build
```


# 프로젝트 디렉토리 구조 설명 (/src 하위 디렉토리 및 파일)
[Back-end]
```bash
.
└── main
    ├── generated
    ├── java
    │   └── com
    │       └── babble
    │           ├── GroupCallApplication.java
    │           ├── api  /* REST API 요청관련 컨트롤러, 서비스, 요청/응답 모델 정의*/
    │           │   ├── controller
    │           │   │   ├── AuthController.java
    │           │   │   ├── RoomController.java
    │           │   │   └── UserController.java
    │           │   ├── request
    │           │   │   ├── room
    │           │   │   │   ├── RoomCreateReq.java
    │           │   │   │   └── RoomRelationReq.java               
    │           │   │   ├── user
    │           │   │   │   ├── UserHashtagReq.java
    │           │   │   │   ├── UserLoginReq.java
    │           │   │   │   ├── UserRegisterReq.java
    │           │   │   │   ├── UserUpdatePasswordReq.java
    │           │   │   │   └── UserUpddatePictureReq.java  
    │           │   ├── response
    │           │   │   ├── RoomRes.java
    │           │   │   ├── UserHistoryRes.java
    │           │   │   ├── UserLoginPostRes.java
    │           │   │   └── UserRes.java
    │           │   └── service
    │           │       ├── CategoryService.java
    │           │       ├── CategoryServiceImpl.java
    │           │       ├── EmailService.java
    │           │       ├── EmailServiceImpl.java
    │           │       ├── HashtagService.java
    │           │       ├── HashtagServiceImpl.java
    │           │       ├── RoomHashtagService.java
    │           │       ├── RoomHashtagServiceImpl.java
    │           │       ├── RoomHistoryService.java
    │           │       ├── RoomHistoryServiceImpl.java
    │           │       ├── RoomService.java
    │           │       ├── RoomServiceImpl.java
    │           │       ├── UserHashtagService.java
    │           │       ├── UserHashtagServiceImpl.java
    │           │       ├── UserRoomService.java
    │           │       ├── UserRoomServiceImpl.java
    │           │       ├── UserService.java
    │           │       └── UserServiceImpl.java
    │           ├── common /* 공용 유틸, 응답 모델, 인증, 예외처리 관련 정의*/
    │           │   ├── auth
    │           │   │   ├── JwtAuthenticationFilter.java
    │           │   │   ├── BabbleUserDetailService.java
    │           │   │   └── BabbleUserDetails.java
    │           │   ├── exception
    │           │   │   └── handler
    │           │   │       └── NotFoundHandler.java
    │           │   ├── model
    │           │   │   └── response
    │           │   │       └── BaseResponseBody.java
    │           │   └── util
    │           │       ├── JwtTokenUtil.java
    │           │       └── ResponseBodyWriteUtil.java
    │           ├── config /* WebMvc 및 JPA, Security, Swagger 등의 추가 플러그인 설정 정의*/
    │           │   ├── EmailConfig.java
    │           │   ├── JpaConfig.java
    │           │   ├── SecurityConfig.java
    │           │   ├── SwaggerConfig.java
    │           │   └── WebMvcConfig.java
    │           └── db /* 디비에 저장될 모델 정의 및 쿼리 구현 */
    │               ├── entity
    │               │   ├── BaseEntity.java
    │               │   ├── Category.java
    │               │   ├── Hashtag.java
    │               │   ├── Room.java
    │               │   ├── RoomHashtag.java
    │               │   ├── RoomHistory.java
    │               │   ├── User.java
    │               │   ├── UserHashtag.java
    │               │   └── UserRoom.java
    │               └── repository
    │                   ├── CategoryRepositorySupport.java
    │                   ├── HashtagRepository.java
    │                   ├── HashtagRepositorySupport.java
    │                   ├── RoomHashtagRepository.java
    │                   ├── RoomHashtagRepositorySupport.java
    │                   ├── RoomHistoryRepository.java
    │                   ├── RoomHistoryRepositorySupport.java
    │                   ├── RoomRepository.java
    │                   ├── RoomRepositorySupport.java
    │                   ├── UserHashtagRepository.java
    │                   ├── UserHashtagRepositorySupport.java
    │                   ├── UserRepository.java
    │                   ├── UserRepositorySupport.java
    │                   ├── UserRoomRepository.java
    │                   └── UserRoomRepositorySupport.java
    └── resources
        ├── README.md
        ├── application.properties /* 웹 리소스(서버 host/port, 디비 host/port/계정/패스워드) 관련 설정 정의 */
        ├── email.properties / * 이메일 전송 관련 설정 정의 * /
        ├── babel.config.js
        ├── dist
        ├── package-lock.json
        ├── package.json
        ├── public
```


```
