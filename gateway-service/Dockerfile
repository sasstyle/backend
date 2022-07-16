FROM openjdk:11-jdk-slim-sid AS builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew clean build

FROM openjdk:11-jdk-slim-sid

VOLUME /tmp

COPY --from=builder build/libs/gateway-service-0.0.1-SNAPSHOT.jar GatewayService.jar

ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /
RUN chmod +x wait-for-it.sh

ENTRYPOINT ["./wait-for-it.sh", "discovery-service:8761", "-t", "20", "--", "java", "-jar", "GatewayService.jar"]