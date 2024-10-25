# Dockerfile
FROM openjdk:17-jdk-slim

# Instala o Git e Python
RUN apt-get update && apt-get install -y git python3 python3-pip maven

# Diretório de trabalho
WORKDIR /

# Clona o repositório e especifica a branch
ARG GIT_BRANCH
ARG GIT_REPO_NAME
ARG GIT_USER
ARG GIT_TOKEN
RUN git clone -b "${GIT_BRANCH}" "https://${GIT_USER}:${GIT_TOKEN}@github.com/${GIT_USER}/${GIT_REPO_NAME}.git" pivi

# Muda para o diretório da aplicação Java
WORKDIR /pivi/app/backend

# Compila o projeto Maven sem executar testes
RUN mvn clean package -DskipTests

# Muda para o diretório dos scripts Python
WORKDIR /pivi/scripts/python

# Executa os scripts Python
RUN python3 create_tables.py && \
    python3 populate_tables.py

# Comando para executar o .jar
CMD ["java", "-jar", "/app/backend/target/seu_projeto.jar"]
