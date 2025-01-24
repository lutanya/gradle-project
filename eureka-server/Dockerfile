FROM gradle:jdk21-alpine AS cache
WORKDIR /opt/app
ENV GRADLE_USER_HOME /cache
COPY build.gradle settings.gradle gradlew ./
COPY gradle /opt/app/gradle
RUN ./gradlew --no-daemon dependencies --stacktrace

FROM gradle:jdk21-alpine AS builder
WORKDIR /opt/app
COPY --from=cache /cache /home/gradle/.gradle
COPY . .
RUN ./gradlew --no-daemon assemble -x test --stacktrace

FROM eclipse-temurin:21-jre-alpine
ARG JAR_FILE=opt/app/build/libs/*.jar
COPY --from=builder ${JAR_FILE} /opt/app/eurekag-server.jar
EXPOSE 8761
CMD ["java","-jar","/opt/app/eurekag-server.jar"]
