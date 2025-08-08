# Usa uma imagem base oficial do Java 17. É uma versão leve, ótima para produção.
FROM openjdk:17-slim

# Define o diretório de trabalho dentro do container.
WORKDIR /app

# Copia os arquivos do Maven Wrapper para o container.
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Baixa as dependências do projeto. Isso otimiza o build,
# pois as dependências só são baixadas novamente se o pom.xml mudar.
RUN ./mvnw dependency:go-offline

# Copia o código-fonte da sua aplicação.
COPY src ./src

# Executa o comando de build do Maven para compilar e empacotar a aplicação.
# -DskipTests pula a execução dos testes para acelerar o deploy.
RUN ./mvnw package -DskipTests

# Expõe a porta 8080 para que o Render possa se conectar à sua aplicação.
EXPOSE 8080

# O comando final que será executado para iniciar sua aplicação.
CMD ["java", "-jar", "target/livraria-0.0.1-SNAPSHOT.jar"]