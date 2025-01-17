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

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-aop")

	implementation("org.springframework.retry:spring-retry:2.0.11")
	implementation("org.apache.tika:tika-core:3.0.0")
	implementation("org.apache.tika:tika:3.0.0")
	implementation("org.apache.tika:tika-parsers-standard-package:3.0.0")
	implementation("org.apache.tika:tika-parsers:3.0.0")
	implementation("org.apache.httpcomponents.client5:httpclient5:5.4.1")

	compileOnly("org.projectlombok:lombok:$lombokVersion")
	runtimeOnly("org.postgresql:postgresql:$postgresqlVersion")
	annotationProcessor("org.projectlombok:lombok:$lombokVersion")

}
