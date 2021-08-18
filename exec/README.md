![waving](https://capsule-render.vercel.app/api?type=waving&height=200&text=Ba:bble&animation=fadeIn&fontAlign=20&fontAlignY=35&fontColor=ffffff&color=#b642f5)


## Getting Started

이 곳에서 설치에 관련된 이야기를 해주시면 좋습니다.

### Prerequisites / 선행 조건

아래 사항들이 설치가 되어있어야합니다.

```
Ubuntu          :   20.04 (Focal Fossa)

JDK             :   8-jdk-alpine

Docker          :   20.10.8
Docker-compose  :   1.29.2
        └── Openvidu/openvidu-server    :   2.19.0
        └──         /coturn             :   5.0.0 
        └──         /proxy              :   7.0.0
        └── kurento/kurento-media-server:   6.16.0
        └── babble (java -jar)          :   1.0     //0.0.0.0:443->8080/tcp

Mysql           :   5.6  // 0.0.0.0:3306->3306

```

[Openvidu](https://docs.openvidu.io/en/2.19.0/deployment/ce/on-premises/)
```bash
# OpenVidu configuration
DOMAIN_OR_PUBLIC_IP=i5a308.p.ssafy.io

# OpenVidu SECRET used for apps to connect to OpenVidu server and users to access to OpenVidu Dashboard
OPENVIDU_SECRET=BABBLE

# - letsencrypt: Generate a new certificate using letsencrypt. Please set the
#                required contact email for Let's Encrypt in LETSENCRYPT_EMAIL
#                variable.
CERTIFICATE_TYPE=letsencrypt

# If CERTIFICATE_TYPE=letsencrypt, you need to configure a valid email for notifications
LETSENCRYPT_EMAIL=ppko1233@gmail.com

# Changes the port of all services exposed by OpenVidu.
# SDKs, REST clients and browsers will have to connect to this port
HTTPS_PORT= 8443
```

[Mysql](https://dev.mysql.com/doc/mysql-installation-excerpt/5.6/en/linux-installation.html)
```bash

# User name for MySQL account to be created
MYSQL_USER = ssafy

# Password for the user account
MYSQL_PASSWORD = ssafy

# Database name
MYSQL_DATABASE = BABBLE

# Password for the root user (optional)
MYSQL_ROOT_PASSWORD = ssafy

```

[Babble](https://i5a308.p.ssafy.io/)
```Bash
# IDE 
VsCode      : Vue3    
IntelliJ
    - Project SDK : corretto-11
    - 
        OAUTH????????????/
```

### Other external services
[Amazon S3](https://aws.amazon.com/ko/?nc2=h_lg)
```JSON
albumBucketName : "babble-test-zimin",
bucketRegion    : "ap-northeast-2",
IdentityPoolId  : "ap-northeast-2:bc050f66-b34f-4742-be97-12b75f402f1f",
```

[Oauth/Kakao](https://developers.kakao.com/product/kakaoLogin)
```JSON
client_id       :   "b571e5a822cacd3d5f0fdec309364338"
redirect_uri    :   "https://i5a308.p.ssafy.io/:8443/oauth/callback/kakao"
client_secret   :   "EbRMgQpvBg7CuKhOtJYsri2Xvln0lRbn"
```
[Oauth/Google](https://developers.google.com/identity/protocols/oauth2)
```JSON
client_id       :   "229511140118-31d4vp160c7dd1ld4g27180fmq1qesg8.apps.googleusercontent.com"
redirect_uri    :   "https://i5a308.p.ssafy.io/:8443/login/oauth2/code/google"
client_secret   :   "kjfp2GRnn0NAmjEwdrG6oo6m"
```
[랜덤 아이디 생성 API](https://nickname.hwanmoo.kr/)
```Bash
# 방 입장 시 랜덤 닉네임 생성에 사용

# API Description
GET https://nickname.hwanmoo.kr/?format=json&count=2

#Example of response
{
    "words":["읽씹하는 우리형","아침까지 게임하는 개발자"],
    "seed":"hwanmoo.yong"
}
```


### Installing / 설치

아래 사항들로 현 프로젝트에 관한 모듈들을 설치할 수 있습니다.

```
예시
```
---

## Running the tests / 테스트의 실행

어떻게 테스트가 이 시스템에서 돌아가는지에 대한 설명을 합니다

### 테스트는 이런 식으로 동작합니다

왜 이렇게 동작하는지, 설명합니다

```
예시
```

### 테스트는 이런 식으로 작성하시면 됩니다

```
예시
```

## Deployment / 배포

Add additional notes about how to deploy this on a live system / 라이브 시스템을 배포하는 방법

## Built With / 누구랑 만들었나요?

* [이름](링크) - 무엇 무엇을 했어요
* [Name](Link) - Create README.md

## Contributiong / 기여

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us. / [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) 를 읽고 이에 맞추어 pull request 를 해주세요.

## License / 라이센스

This project is licensed under the MIT License - see the [LICENSE.md](https://gist.github.com/PurpleBooth/LICENSE.md) file for details / 이 프로젝트는 MIT 라이센스로 라이센스가 부여되어 있습니다. 자세한 내용은 LICENSE.md 파일을 참고하세요.

## Acknowledgments / 감사의 말

* Hat tip to anyone whose code was used / 코드를 사용한 모든 사용자들에게 팁
* Inspiration / 영감
* etc / 기타