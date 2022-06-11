FROM openjdk:11-jdk-slim-sid

VOLUME /tmp

COPY build/libs/discovery-service-0.0.1-SNAPSHOT.jar DiscoveryService.jar

ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /
RUN chmod +x wait-for-it.sh

ENTRYPOINT ["./wait-for-it.sh", "config-service:8888", "-t", "20", "--", "java", "-jar", "DiscoveryService.jar"]