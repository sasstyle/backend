FROM openjdk:11-jdk-slim-sid

VOLUME /tmp

COPY build/libs/config-service-0.0.1-SNAPSHOT.jar ConfigService.jar

ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /
RUN chmod +x wait-for-it.sh

ENTRYPOINT ["./wait-for-it.sh", "rabbitmq:15672", "-t", "20", "--", "java", "-jar", "ConfigService.jar"]