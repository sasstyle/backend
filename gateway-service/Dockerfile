FROM openjdk:11-jdk-slim-sid

VOLUME /tmp

COPY build/libs/gateway-service-0.0.1-SNAPSHOT.jar GatewayService.jar

ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /
RUN chmod +x wait-for-it.sh

ENTRYPOINT ["./wait-for-it.sh", "discovery-service:8761", "-t", "20", "--", "java", "-jar", "GatewayService.jar"]