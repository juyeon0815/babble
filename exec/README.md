![transparent](https://capsule-render.vercel.app/api?type=transparent&fontColor=703ee5&text=Ba:bble&height=150&fontSize=60&desc=당신을%20위한%20공간&descAlignY=75&descAlign=60)
## Getting Started

### Prerequisites / 선행 조건

아래 사항들이 설치가 되어있어야합니다.

```
Ubuntu          :   20.04 (Focal Fossa)
    └── Docker          :   20.10.8
            └── JDK :   8-jdk-alpine
        Docker-compose  :   1.29.2
            └── Openvidu/openvidu-server    :   2.19.0
            └──         /coturn             :   5.0.0 
            └──         /proxy              :   7.0.0
            └── kurento/kurento-media-server:   6.16.0
            └── babble (java -jar)          :   1.0     //0.0.0.0:443->8080/tcp
            └── Mysql           :   5.6  // 0.0.0.0:3306->3306

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
```bash
SDK     :   corretto-11
.gradle :   6.7

# email.properties
mail.smtp.auth=true
mail.smtp.starttls.required=true
mail.smtp.starttls.enable=true
mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory
mail.smtp.socketFactory.fallback=false
mail.smtp.port=465
mail.smtp.socketFactory.port=465

AdminMail.id=babble0802@gmail.com
AdminMail.password=babble0802pass

# Dockerfile
FROM openjdk:8-jdk-alpine
ADD babble-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]
```

### Other external services
[Amazon S3](https://aws.amazon.com/ko/?nc2=h_lg)
```json
albumBucketName : "babble-test-zimin",
bucketRegion    : "ap-northeast-2",
IdentityPoolId  : "ap-northeast-2:bc050f66-b34f-4742-be97-12b75f402f1f",
```

[Oauth/Kakao](https://developers.kakao.com/product/kakaoLogin)
```json
client_id       :   "b571e5a822cacd3d5f0fdec309364338"
redirect_uri    :   "https://i5a308.p.ssafy.io/:8443/oauth/callback/kakao"
client_secret   :   "EbRMgQpvBg7CuKhOtJYsri2Xvln0lRbn"
```
[Oauth/Google](https://developers.google.com/identity/protocols/oauth2)
```json
client_id       :   "229511140118-31d4vp160c7dd1ld4g27180fmq1qesg8.apps.googleusercontent.com"
redirect_uri    :   "https://i5a308.p.ssafy.io/:8443/login/oauth2/code/google"
client_secret   :   "kjfp2GRnn0NAmjEwdrG6oo6m"
```
[랜덤 아이디 생성 API](https://nickname.hwanmoo.kr/)
```bash
# 방 입장 시 랜덤 닉네임 생성에 사용

# API Description
GET https://nickname.hwanmoo.kr/?format=json&count=2

#Example of response
{
    "words":["읽씹하는 우리형","아침까지 게임하는 개발자"],
    "seed":"hwanmoo.yong"
}
```


## Running the tests

```bash
cd /opt/openvidu

# mysql 설정 
https://jasper-aardwolf-29e.notion.site/MySQL-4b79fed9d6f940729865a8dca40debec

#오픈비두 실행
sudo ./openvidu start

#앱 배포 및 실행
sudo docker build --tag babble .
sudo docker run -p 443:8080 babble

# You Can Access 
# [https://i5a308.p.ssafy.io]
```


---
## [Scenario 바로가기](./senario.md)
