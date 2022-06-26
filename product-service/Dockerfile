FROM openjdk:11-jdk-slim-sid

VOLUME /tmp

COPY build/libs/product-service-0.0.1-SNAPSHOT.jar ProductService.jar

ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /
RUN chmod +x wait-for-it.sh

ENTRYPOINT ["./wait-for-it.sh", "gateway-service:8000", "-t", "20", "--", "java", "-jar", "ProductService.jar"]