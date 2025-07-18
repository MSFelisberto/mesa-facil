FROM maven:3.9.9-eclipse-temurin-21-alpine AS build

WORKDIR /app

COPY src /app/src
COPY pom.xml /app

RUN mvn clean install -DskipTests

FROM eclipse-temurin:21-jre-alpine

# Copiar o JAR compilado do estágio de build
COPY --from=build /app/target/mesafacil-0.0.1-SNAPSHOT.jar /app/maesafacil.jar

WORKDIR /app

EXPOSE 8080

CMD ["sh", "-c", "sleep 15 && java -jar app.jar"]