FROM maven:3.8.5-openjdk-17 AS maven-builder

WORKDIR /app

COPY pom.xml .
COPY src src

RUN mvn clean package -DskipTests -Dmaven.javadoc.skip=true -B -V

FROM openjdk:17-alpine

WORKDIR /app

COPY --from=maven-builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]