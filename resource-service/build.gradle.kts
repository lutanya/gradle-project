plugins {
	java
	id("org.springframework.boot") version "3.4.1"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.epam.learn.microcervices"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

val postgresqlVersion ="42.7.4"
val lombokVersion = "1.18.36"
val tikaVersion = "3.0.0"
val httpclientVersion = "5.4.1"
val springRetryVersion = "2.0.11"
val springCloudVersion = "2024.0.0"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-aop")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
	implementation("org.springframework.cloud:spring-cloud-starter-loadbalancer")
	implementation("org.springframework.retry:spring-retry:$springRetryVersion")

	implementation("org.apache.tika:tika-core:$tikaVersion")
	implementation("org.apache.tika:tika:$tikaVersion")
	implementation("org.apache.tika:tika-parsers-standard-package:$tikaVersion")
	implementation("org.apache.tika:tika-parsers:$tikaVersion")
	implementation("org.apache.httpcomponents.client5:httpclient5:$httpclientVersion")

	compileOnly("org.projectlombok:lombok:$lombokVersion")
	runtimeOnly("org.postgresql:postgresql:$postgresqlVersion")
	annotationProcessor("org.projectlombok:lombok:$lombokVersion")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}")
	}
}
