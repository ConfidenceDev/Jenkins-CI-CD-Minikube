FROM openjdk:14-alpine

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} cicd.jar

ENTRYPOINT ["java", "-jar", "/cicd.jar"]

EXPOSE 8082