<h1>Spring Boot - MVC </h1>
<i align="center">해당 Repo는 Spring Boot, Spring MVC 기본적인 구조에 대한 학습을 위해 만들어졌습니다.</i>

<h4 align="center">
  <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=flat-square&logo=Kotlin&logoColor=white" />
  <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=Spring Boot&logoColor=white" />
  <img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=flat-square&logo=PostgreSQL&logoColor=white" />
</h4>

---

## 🏃How To Run
- 사전 필요조건
    - docker-compose.yaml로 docker구동 필요
        - postgresql 구동된 상태
    - 설정 확인
        - 위치 : resources/config/application-local.yaml

        ```yaml
        spring:
          datasource:
            url: jdbc:postgresql://localhost:5432/test_db
            username: test_user
            password: test_password
            driverClassName: org.postgresql.Driver
          jpa:
            hibernate:
              ddl-auto: create
        ```
- (IntelliJ 기준) Edit Configuration 에서 Active profile 을 `local` 로 설정

## 📄 **Document**

1. Swagger
> [http://localhost:8000/swagger-ui/index.html](http://localhost:8000/swagger-ui/index.html)

2. Spring REST Docs
> [http://localhost:8000/docs/index.html](http://localhost:8000/docs/index.html)


## 🗂️ **Structure**
```bash
src.docs.asciidoc
├── index.adoc            # 프로젝트 API 문서화를 위한 RestDocs 설정 포함

src.main.kotlin.com.sample.spring_mvc
├── config                # 애플리케이션 설정
├── controller            # REST API 엔드포인트
├── domain                # API 계층 및 데이터베이스 엔티티를 위한 데이터 전송 객체
  ├── dto                 # API 계층을 위한 데이터 전송 객체
  ├── entity              # 데이터베이스 엔티티
└── mapper                # DTO와 엔티티 간 변환을 위한 MapStruct 매핑 클래스
  ├── custom              # MapStruct를 사용하지 않는 맞춤형 매핑 클래스
└── repository            # JPA 리포지토리
└── service               # 서비스 클래스(비즈니스 로직)
└── SpringMvcApplication.kt  # 애플리케이션 시작점

src.main.resources
├── config                # 프로젝트 설정(공통, 로컬, 개발, 프로덕션)
├── static.docs
   ├── index.html         # Restdocs 출력

src.test.kotlin.com.sample.spring_mvc
├── controller            # Restdocs 설정을 포함한 컨트롤러 계층 테스트


build.gradle.kts          # gradle 코틀린 DSL 스크립트
docker-compose.yml        # docker compose 파일
```
