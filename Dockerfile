# Estágio de build
FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar arquivos do projeto
COPY . .

# Compilar e empacotar o projeto
RUN mvn clean package -DskipTests

# Estágio de execução
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiar o JAR compilado do estágio de build
COPY --from=build /app/target/*.jar /app/maesafacil.jar

# Expor a porta da aplicação
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "/app/maesafacil.jar"]