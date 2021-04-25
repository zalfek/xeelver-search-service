FROM alpine:3.13.5

RUN apk add openjdk11
COPY target/search-service.jar /app.jar
EXPOSE 80
ENTRYPOINT ["java", "-jar", "/app.jar"]