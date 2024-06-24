// 플러그인 설정
plugins {
	// Spring Boot 애플리케이션을 지원하는 플러그인, 스프링 부트 프로젝트를 쉽게 만들고 관리할 수 있음.
	id("org.springframework.boot") version "3.3.0"

	// Spring 의존성 관리 플러그인, 스프링 부트 프로젝트의 의존성 버전을 관리하는 데 사용
	id("io.spring.dependency-management") version "1.1.5"

	// Asciidoctor를 사용한 문서 변환 플러그인, 마크다운이나 AsciiDoc 문서를 다양한 포맷으로 변환할 때 사용
	id("org.asciidoctor.jvm.convert") version "3.3.2"

	// Kotlin JVM 플러그인, Kotlin 코드를 JVM 바이트코드로 컴파일하는 기본 설정을 제공
	kotlin("jvm") version "1.9.24"

	// Kotlin JVM 플러그인, Kotlin 코드를 JVM 바이트코드로 컴파일하는 기본 설정을 제공
	kotlin("plugin.spring") version "1.9.24"

	// Kotlin JPA 플러그인, JPA 엔티티 클래스에 open 수정자를 자동으로 추가하여 JPA 구현체에서 프록시를 사용할 수 있도록 함
	kotlin("plugin.jpa") version "1.9.20"

	// Kotlin Annotation Processing Tool(KAPT), 애노테이션 프로세서를 사용하여 Kotlin 코드에서 애노테이션 기반 작업을 할 수 있게 함
	kotlin("kapt") version "1.9.20"

	// Hibernate ByteCode Enhancement 플러그인, 엔티티 클래스의 바이트코드를 조작하여 더티 체킹과 지연 로딩과 같은 ORM 최적화 기능을 제공
	id("org.hibernate.orm") version "6.4.1.Final"
}

// allOpen 설정: 특정 애노테이션이 붙은 클래스를 컴파일 타임에 open 클래스로 만듦. 이는 JPA를 사용할 때 프록시 객체 생성을 위해 필요함
allOpen {
	// JPA 엔티티 클래스에 대해 open 클래스로 만들도록 설정
	annotation("jakarta.persistence.Entity")
	// JPA 상속 계층에서 사용되는 클래스에 대해 open 클래스로 만들도록 설정
	annotation("jakarta.persistence.MappedSuperclass")
	// JPA 내장 타입에 대해 open 클래스로 만들도록 설정
	annotation("jakarta.persistence.Embeddable")
}

// Hibernate 바이트코드 향상 설정: Hibernate 성능을 개선하기 위한 바이트코드 레벨의 최적화 설정
hibernate {
	enhancement {
		// 지연 로딩 활성화: 관련 객체를 실제로 사용할 때까지 로딩하지 않음
		enableLazyInitialization = true
		// 더티 체킹 활성화: 객체의 변경 사항을 자동으로 감지하여 데이터베이스에 반영
		enableDirtyTracking = true
		// 연관 관계 관리 활성화: 객체 간의 관계를 자동으로 관리
		enableAssociationManagement = true
		// 확장된 향상 비활성화: 특정 최적화를 제외한 나머지 최적화 사용
		enableExtendedEnhancement = false
	}
}

// 프로젝트 그룹 ID와 버전 설정
group = "com.sample"
version = "v1.0.0"

// Asciidoctor 확장을 위한 커스텀 구성 생성
val asciidoctorExt by configurations.creating

// QueryDSL 라이브러리의 버전을 변수로 설정
val queryDslVersion = "5.1.0"

// Java 소스 호환성 설정: JDK 17 사용
java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

// 리포지토리 설정: 의존성을 가져올 리포지토리 설정
repositories {
	// Maven 중앙 리포지토리 사용
	mavenCentral()
	// Gradle 플러그인 포털 사용
	gradlePluginPortal()
	// Confluent 패키지를 위한 Maven 리포지토리 추가
	maven {
		url = uri("https://packages.confluent.io/maven")
	}
}


// snippetsDir 변수 선언: 테스트 중 생성된 스니펫을 저장할 디렉토리 경로
extra["snippetsDir"] = file("build/generated-snippets")

dependencies {
	// Spring Boot Starter Web: 웹 애플리케이션 개발을 위한 스타터 키트
	implementation("org.springframework.boot:spring-boot-starter-web")
	// Spring Boot Starter JPA: JPA를 이용한 데이터 접근을 위한 스타터 키트
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	// Jackson: JSON 처리를 위한 라이브러리
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	// Kotlin Reflection: 코틀린 언어의 리플렉션 기능을 사용하기 위한 라이브러리
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	// Spring Doc: 스프링 애플리케이션을 위한 OpenAPI 문서 자동 생성 도구
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.4.0"){
		exclude(group = "io.swagger.core.v3", module = "swagger-annotations")
	}
	implementation("io.swagger.core.v3:swagger-annotations:2.2.20")
	implementation("org.springdoc:springdoc-openapi-starter-common:2.4.0")

	// MapStruct: 객체 간 매핑을 쉽게 할 수 있게 해주는 코드 생성 라이브러리
	implementation("org.mapstruct:mapstruct:1.5.5.Final")
	kapt("org.mapstruct:mapstruct-processor:1.5.5.Final")

	// Querydsl JPA: 타입 세이프한 쿼리를 위한 DSL
	implementation("com.querydsl:querydsl-jpa:${queryDslVersion}:jakarta")
	implementation("com.querydsl:querydsl-apt:${queryDslVersion}:jakarta")

	// Postgresql: PostgreSQL 데이터베이스 드라이버
	runtimeOnly("org.postgresql:postgresql")

	// Spring Boot Test: 스프링 부트 기반의 테스트를 위한 의존성
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	// Spring Rest Docs: API 문서화를 위한 Asciidoctor 연동
	testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
}

// Kotlin 컴파일러 작업에 대한 설정
kotlin {
	compilerOptions {
		// 컴파일러에 추가적인 인자를 제공, JSR-305 어노테이션과 관련된 컴파일러의 엄격한 처리를 활성화
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

// 테스트 작업에 대한 설정
tasks.withType<Test> {
	// JUnit Platform을 사용하여 테스트 실행
	useJUnitPlatform()
}

// test 작업 설정: 테스트 실행 관련 설정
tasks.test {
	outputs.dir(project.extra["snippetsDir"]!!)
}

tasks.asciidoctor {
	inputs.dir(project.extra["snippetsDir"]!!)
	dependsOn(tasks.test)
}
