## 🙌 다같이 공부하기 다각 포팅메뉴얼입니다.

## ❓  프로젝트 개요
많은 온라인 스터디 플랫폼에서 공부를 하기위해서는 스터디 그룹을 모아야 한다는 단점과 혼자 공부하기 힘든 사람들을 위해 다 같이 모여서 랜덤 매칭을 통해 공부할 수 있는 공간을 제공해주는 서비스를 만들었습니다.
<br>
기능으로는 포인트, 아바타 꾸미기, 랜덤 방 매칭, 스케쥴 관리, 화상 공부방을 주 기능으로 제공하고 있습니다.

### 프로젝트 사용 도구
이슈 관리 : JIRA <br>
형상 관리 : Gitlab <br>
커뮤니케이션 : Notion, Mattermost <br>
디자인 : Figma <br>
CI/CD : Jenkins

### 개발환경 
IDEA : Intellj 2023.3.2, visual_studio_code version 1.85 <br>
JDK : corretto-17 <br>
SPRING_BOOT : 2.7.17 <br>
NPM : v20.10.0   <br>
VITE : 5.0.10 <br>
MYSQL : 8.0.33


<hr>

## 목차 
[오픈비두설치](#오픈비두설치)
[환경설정](#환경설정)
<br>
[빌드하기](#빌드하기)
<br>
[배포하기](#배포하기)

## 오픈비두설치 및 설정
1. 다음 주소로 가서 따라 설치한다. <a href="https://docs.openvidu.io/en/stable/deployment/ce/on-premises/">오픈비두 설치</a> <br>
(오픈비두를 배포하기 root 권한을 얻어야 함 - sudo su) <br>
(오픈비두를 설치하기 위해 권장되는 경로인 /opt로 이동- cd /opt)
```
설치 후 오픈비두가 설치된 경로로 이동
$ cd openvidu
도메인 또는 퍼블릭IP와 오픈비두와 통신을 위한 환경설정
$ nano .env

# OpenVidu configuration
# ----------------------
# 도메인 또는 퍼블릭IP 주소
DOMAIN_OR_PUBLIC_IP=내도메인 혹은 아이피 주소

# 오픈비두 서버와 통신을 위한 시크릿
OPENVIDU_SECRET=시크릿키 -> 백엔드 오픈비두 SCRET KEY와 동일하게 설정

# Certificate type
CERTIFICATE_TYPE=letsencrypt

# 인증서 타입이 letsencrypt일 경우 이메일 설정
LETSENCRYPT_EMAIL=user@example.com

# HTTP port
HTTP_PORT=80

# HTTPS port(해당 포트를 통해 오픈비두 서버와 연결)
HTTPS_PORT=443
```
2. 오픈비두 niginx custom을 하기 위해 다음 주소로 가서 따라 한다. <a href="https://docs.openvidu.io/en/stable/troubleshooting/#16-how-can-i-customize-deployed-nginx">오픈비두 niginx custom</a> <br>
3. 복사한 niginx 파일에서 다음과 같이 추가한다.
```nginx

# BACK
upstream backend {
    server 172.17.0.1:8080;
}
# FRONT
upstream frontend {
    server 172.17.0.1:8000;
}

upstream openviduserver {
    server 172.17.0.1:5443;
}

server {
    listen 80;
    listen [::]:80;
    server_name i10a404.p.ssafy.io;

        # Redirect to https
    location / {
        proxy_pass http://frontend;
    }
    location /dagak {
        proxy_pass http://backend;
    }

    location /nginx_status {
        stub_status;
        allow 127.0.0.1;        #only allow requests from localhost
        deny all;               #deny all other hosts
    }
}



server {
    listen 443 ssl;
    listen [::]:443 ssl;
    server_name i10a404.p.ssafy.io;

    # Redirect to https
    location / {
        proxy_pass http://frontend;
    }
    location /dagak {
        proxy_pass http://backend;
    }
    # SSL Config
    ssl_certificate         /etc/letsencrypt/live/발급도메인/fullchain.pem;
    ssl_certificate_key     /etc/letsencrypt/live/발급도메인/privkey.pem;
    ssl_trusted_certificate /etc/letsencrypt/live/발급도메인/fullchain.pem;

    ssl_session_cache shared:SSL:50m;
    ssl_session_timeout 5m;
    ssl_stapling on;
    ssl_stapling_verify on;

    ssl_protocols TLSv1.2 TLSv1.3;
    ssl_ciphers "ECDHE-ECDSA-AES128-GCM-SHA256:ECDHE-RSA-AES128-GCM-SHA256:ECDHE-ECDSA-AES256-GCM-SHA384:ECDHE-RSA-AES256-GCM-SHA384:ECDHE-ECDSA-CHACHA20-POLY1305:ECDHE-RSA-CHACHA20-POLY1305:DHE-RSA-AES128-GCM-SHA256:DHE-RSA-AES256-GCM-SHA384";
    ssl_prefer_server_ciphers off;

    add_header Strict-Transport-Security "max-age=63072000" always;

    # Proxy
    proxy_set_header Host $host;
    proxy_set_header X-Real-IP $remote_addr;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    proxy_set_header X-Forwarded-Proto $scheme;
    proxy_set_header X-Forwarded-Proto https;
    proxy_headers_hash_bucket_size 512;
    proxy_redirect off;

    # Websockets
    proxy_http_version 1.1;
    proxy_set_header Upgrade $http_upgrade;
    proxy_set_header Connection "upgrade";


    ########################
    # OpenVidu Locations   #
    ########################
    #################################
    # Common rules CE              #
    #################################
    # Dashboard rule
    location /dashboard {
        allow all;
        deny all;
        proxy_pass http://openviduserver;
    }

    # Websocket rule
    location ~ /openvidu$ {
        proxy_pass http://openviduserver;
    }


    #################################
    # New API                       #
    #################################
    location /openvidu/layouts {
        rewrite ^/openvidu/layouts/(.*)$ /custom-layout/$1 break;
        root /opt/openvidu;
    }

    location /openvidu/recordings {
        proxy_pass http://openviduserver;
    }

    location /openvidu/api {
        allow all;
        deny all;
        proxy_pass http://openviduserver;
    }

    location /openvidu/info {
        allow all;
        deny all;
        proxy_pass http://openviduserver;
    }

    location /openvidu/accept-certificate {
        proxy_pass http://openviduserver;
    }

    location /openvidu/cdr {
        allow all;
        deny all;
        proxy_pass http://openviduserver;
    }

    #################################
    # LetsEncrypt                   #
    #################################
    location /.well-known/acme-challenge {
        root /var/www/certbot;
        try_files $uri $uri/ =404;
    }

}
```
3. ./openvidu start 로 오픈비두를 시작한다.

## 환경설정  

### DataBase
1. mySQL 8.0.33 버전을 다운받는다
2. create database dagak, security 로 디비 두개를 생성한다.
3. 현재 폴더에 있는 DB SQL을 실행한다.
### Redis
<a href="https://pamyferret.tistory.com/9">레디스 설치 방법</a><br>
도커가 설치되어있다면  
docker run -itd --name redis-container -p 6379:6379 redis

### 깃 풀 받기
1. git Bash 를 설치한다.
2. git clone "프로젝트url 복사한 주소"

### 프론트 빌드파일 수정하기
서버에 올리기 위해서 프론트엔드 .env, vite.conifg.js 를 수정해야 합니다. 프론트엔드 폴더로 가주세요.
#### env
```text
VITE_API_BASE_URL = 서버_API주소입력(스프링부트URL)
VITE_OPENVIDU_SERVER_URL = 오픈비두서버주소입력
VITE_RECAPTCAH_KEY = 로그인리캡챠서비스키등록
```
#### vite.config.js
https 설정 제거 아래처럼 작성
```
import { fileURLToPath, URL } from 'node:url';
import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';


// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
    },
  },
  // SCSS 전역 사용
  css: {
    preprocessorOptions: {
      scss: {
        additionalData: '@import "@/assets/common.scss";',
      },
    },
  },
});
```

### 백엔드 빌드파일 수정하기 
```yaml
spring:
  servlet:
    multipart:
      max-file-size: 10MB
      max-request-size: 10MB
  mail:
    host: smtp.gmail.com
    port: 587
    username: stmp메일주소
    password: 받은키
    properties:
      mail:
        smtp:
          starttls:
            enable: true
          auth: true

  mvc:
    pathmatch:
      matching-strategy: ant_path_matcher
  dagak:
    datasource:
      driver-class-name: com.mysql.cj.jdbc.Driver
      jdbc-url: jdbc:mysql://디비설치주소/dagak
      username: 이름
      password: 패스워드
  security:
    datasource:
      driver-class-name: com.mysql.cj.jdbc.Driver
      jdbc-url: jdbc:mysql://디비설치주소/security
      username: 이름
      password: 패스워드
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    jdbc-url: jdbc:mysql://디비설치주소/dagak?serverTimezone=UTC&useLegacyDatetimeCode=false
    username: 이름
    password: 패스워드
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    database: mysql
    properties:
      database-platform: org.hibernate.dialect.MySQL5InnoDBDialect
      hibernate:
        implicit_naming_strategy: org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy
        physical_naming_strategy: org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy
    h2:
      console:
        enabled: true
  redis:
    host: 레디스주소
    port: 6379
    password: 레디스패스워드없다면 지우세요
  mybatis:
    mapper-locations: classpath:mybatis/mappers/**/*.xml
openvidu:
  secret: 오픈비두시크릿키
  url: 오픈비두설치URL
server:
  servlet:
    context-path: /dagak
google:
  auth:
    url: https://oauth2.googleapis.com
    scope: email
  login:
    url: https://accounts.google.com
  redirect.url: https://프론트주소/googleLogin
  client:
    id: 키값
  secret: ENC(mOXhqgPNISKfS9rHypLRVJI/iBKZVWzTJn9wFHfAS0N8tfSGiFo6cGzh685eKCQa)
  recaptcha:
    key: kakao:
  rest-api-key: 키값
  redirect.url: https://프론트주소/kakaoLogin
cloud:
  aws:
    s3:
      bucket: 버켓위치
    region:
      static: ap-northeast-2
      auto: false
    stack:
      auto: false
    credentials:
      access-key: 버켓 키
      secret-key: 버켓 시큐리티 키

```
src/main/java/com/ssafy/backend/common/config JasyptConfig.java 에 파일 추가
```java 
package com.ssafy.backend.common.config;


import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@Configuration
@EnableEncryptableProperties
public class JasyptConfig {
    private static final String KEY = "스트링키";
    private static final String ALGORITHM = "알고리즘선택";

    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(KEY); // 암호화할 때 사용하는 키
        config.setAlgorithm(ALGORITHM); // 암호화 알고리즘
        config.setKeyObtentionIterations("1000"); // 반복할 해싱 회수
        config.setPoolSize("1"); // 인스턴스 pool
        config.setProviderName("SunJCE"); // 프로바이더
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator"); // salt 생성 클래스 지정
        config.setStringOutputType("base64"); // 인코딩
        encryptor.setConfig(config); // 설정 정보 Set
        return encryptor;
    }
}
```

## 빌드하기  
### frontend 
exec/frontend 폴더에서 도커파일과 엔지닉스 설정파일이 없다면 프론트 폴더로 복사한다.
```
1. npm install
2. npm run build
3. docker build -t 이름설정.
```
### backend
프론트와 같이 도커파일이 없다면 백엔드폴더에서 도커파일을 복사해 넣는다.
```
1. chmod 777 ./gradlew
2. ./gradlew build
3. docker buildx build --build-arg JAR_FILE=./build/libs/backend*.jar -t 이름설정 . --load
```
## 배포하기
### 프론트엔드
```
docker run -d -p 8000:80 이미지 이름
```
### 백엔드 
```
docker run -d -p 8080:8080 이미지 이름
```

