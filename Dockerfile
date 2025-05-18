FROM eclipse-temurin:17-jdk-alpine

ARG FULL_JAR_FILE_NAME
ENV JAR_FILE_NAME=${FULL_JAR_FILE_NAME}

RUN apk update && apk add --no-cache curl wget vim net-tools iputils

WORKDIR /app

COPY target/${FULL_JAR_FILE_NAME}.jar /app/${FULL_JAR_FILE_NAME}.jar

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -jar /app/${JAR_FILE_NAME}.jar --spring.profiles.active=${SPRING_PROFILE}"]