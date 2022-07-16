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

COPY --from=builder build/libs/discovery-service-0.0.1-SNAPSHOT.jar DiscoveryService.jar

ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /
RUN chmod +x wait-for-it.sh

ENTRYPOINT ["./wait-for-it.sh", "config-service:8888", "-t", "20", "--", "java", "-jar", "DiscoveryService.jar"]