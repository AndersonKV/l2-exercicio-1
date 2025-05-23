# Usa uma imagem base do Java 17
FROM openjdk:17-jdk-slim

# Cria um diretório para a aplicação
WORKDIR /app

# Copia o JAR gerado pelo build para dentro do container
COPY target/*.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
