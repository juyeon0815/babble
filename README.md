# Ba:bble

## 목차

- [프로젝트 소개](#프로젝트-소개)     
- [프로젝트 명세](#프로젝트-명세)
  - [배포 환경](#배포-환경)
  - [개발 환경](#개발-환경)
  - [Design Resources](#design-resources)
  - [핵심 라이브러리](#핵심-라이브러리)
  <br>

## 🙋 프로젝트 소개
> 관심사에 대해 쉽게, 편하게, 다같이! 떠드는 익명의 플랫폼

<br>

## 🤹 팀원 소개

| 김주현                                                       | 김주연                                                  | 이지민                                                  | 조은지                                                  | 황성현                                                  |
| ------------------------------------------------------------ | ------------------------------------------------------- | ------------------------------------------------------- | ------------------------------------------------------- | ------------------------------------------------------- |
| ![jh.png](/uploads/f099e4146dce0c0946d66ad1430dc0f2/jh.png.jpg) | ![jy](/uploads/88094931803358a39705df43232e1f07/jy.png) | ![jm](/uploads/de0961883a918d25f53d94a7de7b95f9/jm.png) | ![ej](/uploads/e60efdd972598e51c47a6b29f17a7c32/ej.png) | ![sh](/uploads/f305971f3aa2c52a91e65b7c259c7269/sh.png) |
| 팀장 / backend / 채팅                                        | back / API / 회원 관리 / 배포                           | front /                                                 | front /                                                 | backend / frontend / 화상 송출 / 배포                   |
| [j828h@naver.com](j828h@naver.com)                           | [wndusx1@gmail.com](wndusx1@gmail.com)                  | [jm9521@hanmail.net](jm9521@hanmail.net)                | [bagjo28841@gmail.com](bagjo28841@gmail.com)            | [ppko1233@gmail.com](ppko1233@gmail.com)                |

## ✏️ 프로젝트 명세
### 배포 환경 
- __URL__ : https://i5a308.p.ssafy.io
- __배포 여부__ : O
- __접속 가능__ : 접속 가능
- __HTTPS 적용__ : O 
- __PORT__ :  
  - Mysql : 3306
  - Openvidu  - 8443
<br>

### 개발 환경
#### Front-end
- __Framework__ : [Vue.js](https://v3.vuejs.org/guide/introduction.html)
- __지원 환경__ : Web
- __담당자__ : 이지민, 조은지

#### Back-end
- __Framework__ : [Spring boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- __Database__ : [MySQL](https://dev.mysql.com/doc/)
- __담당자__ : 김주현, 김주연, 황성현

#### Design 
- __Framework 사용__ : O 
  - [Element Plus](https://element-plus.org/)
- __Design Tool 사용__ : [Figma](https://www.figma.com/)
- __담당자__ : 이지민, 조은지

### Design Resources 
- 외부 템플릿 또는 에셋 없음 (무료 이미지, 폰트만 사용)

__자체 제작 산출물__ (필요시 이미지 또는 설명 첨부) (미정)
- Logo
- favicon

### 핵심 라이브러리 
- Front : Vuex, Vue Router, Element Plus, CSS media queries, Axios
- Back : JPA, Hiberate, QueryDSL, Lombok

### 서비스 구조
<img src="/uploads/8677ec240c5aeef57954988e1acee826/structure.png" width="80%">

### ERD

<img src="/uploads/d32ce6da95741f1c3c41f57d055ceff9/erd_0814.png" width="80%">



## 🏠 프로젝트 설계

### 프로토타입
- [Figma](https://www.figma.com/file/STAQLXm4jjJnYXJD3aUkRr/Babble?node-id=0%3A1)



## ❗ 주요 기능

1. **[회원 가입 / 로그인]** E-mail / SNS로 쉽게 가입하세요.

   <img src="/uploads/96f53f9bb253b569710429536e233c97/join.png" width="50%">

   <img src="/uploads/2605a6f145d374af8413169df32f72a4/join2.png" width="50%">

   - 비회원도 방 시청 등 서비스 이용이 가능합니다.

   <img src="/uploads/b31c0232ac799341eb07eaefdee11521/login.png" width="50%">

2. **[이야기방 생성]** 원하는 주제의 이야기 방을 생성하세요.

   - 로그인한 사용자는 원하는 이야기 방을 생성할 수 있습니다.  
     방 제목, 설명을 기입하고 썸네일로 지정할 사진을 업로드 후 분류 카테고리를 지정합니다.

     <img src="/uploads/e31b627099be4115990126437a017402/create.png" width="50%">

   - 원하는 해시테그 키워드를 5개까지 설정할 수 있습니다.  
     해당 키워드 구독을 원하는 사용자에게 이야기 방 알림 메일이 전송됩니다!

     <img src="/uploads/25b03ee9ccacfda98cc7c5d487dd164c/alarm.png" width="50%">

   - 호스트가 방을 나가면 방은 사라집니다.

     <img src="/uploads/f68eaef165839f3524b98478f59165b7/exit.png" width="50%">

3. **[이야기방 참여]** 생성된 이야기 방에 참여하세요.

   <img src="/uploads/1a086db0767af8171688f4f1b6ac9158/room.png" width="50%">

   <img src="/uploads/b79d7205dcf7f6d95f73df1c46477156/enter.png" width="50%">

   - 모든 유저는 익명의 닉네임을 부여받아 이야기 방에 참여합니다.

   - 로그인한 사용자는 대기실에서 방송 입장 전 카메라 / 음성 송출 여부를 지정.후 방에 입장합니다.  
     방 입장 후에도 송출 여부를 변경할 수 있습니다.

   - 방 입장 후 이모티콘과 채팅을 통해 리액션을 보여주세요.

     - 로그인 하지 않은 사용자는 방송 청취와 이모티콘 리액션, 채팅 보기만가능합니다.

     <img src="/uploads/fe17855ed0a38918278112b3e49a04a9/chat.png" width="30%">

     <img src="/uploads/797f5753d7f83191ff1e7bc36bf48d9d/emoji.png" width="30%">

4. **[마이페이지]** 마이 페이지에서 회원 정보를 수정하세요

   - 최대 5개까지 알림 설정을 원하는 해시테그 키워드를 설정할 수 있습니다. 알람 설정이 on이라면 해당 키워드의 이야기 방이 개설되면 이메일 알람을 보내드립니다.

     <img src="/uploads/09c6ab4f84891f85aac6f320ca0ca1c7/keyword.png" width="30%">

   - 이야기 방 시청 이력과 방 생성 이력을 확인할 수 있습니다.

     <img src="/uploads/e6210e160f71f1d256b5120700f11f10/mypage.png" width="60%">


