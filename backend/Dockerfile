FROM gradle:7.6.0-jdk17-jammy AS build

WORKDIR /home/gradle/rsoi

COPY settings.gradle.kts ./
COPY build.gradle.kts ./
COPY gradle.properties ./

COPY util ./util
COPY reservation-service ./reservation-service
COPY loyalty-service ./loyalty-service
COPY payment-service ./payment-service
COPY gateway-service ./gateway-service

ENV JAVA_OPTS="-Xmx2g -XX:MaxMetaspaceSize=512m -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8"
ENV GRADLE_OPTS="-Xmx2g -XX:MaxMetaspaceSize=512m -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8"

RUN gradle :reservation-service:bootJar \
           :loyalty-service:bootJar \
           :payment-service:bootJar \
           :gateway-service:bootJar \
           --no-daemon --parallel

FROM eclipse-temurin:17-jre-jammy

ARG PROJECT=missing-project-use-build-args

ENV LANG en_US.UTF-8
EXPOSE 8080

COPY --from=build /home/gradle/rsoi/${PROJECT}/build/libs/${PROJECT}-*-all.jar /app/app.jar
#CMD tail -f /dev/null
ENTRYPOINT ["java", "-jar", "-Djava.awt.headless=true", "-Dspring.profiles.active=prod", "/app/app.jar"]
