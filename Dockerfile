FROM openjdk:17-jdk-slim

# Instala o Git, Python e Maven
RUN apt-get update && apt-get install -y git python3 python3-pip maven

# Diretório de trabalho
WORKDIR /

# Clona o repositório e especifica a branch
ARG GIT_BRANCH
ARG GIT_REPO_NAME
ARG GIT_USER
ARG GIT_TOKEN

# Verifica se o diretório já existe; se sim, faz pull, se não, clona
RUN if [ ! -d "${GIT_REPO_NAME}" ]; then git clone -b "${GIT_BRANCH}" "https://${GIT_USER}:${GIT_TOKEN}@github.com/${GIT_USER}/${GIT_REPO_NAME}.git"; else git fetch -a && git pull origin "${GIT_REPO_NAME}"; fi

# Muda para o diretório da aplicação Java
WORKDIR /${GIT_REPO_NAME}/app/backend

# Compila o projeto Maven sem executar testes
RUN mvn clean package -DskipTests

# Muda para o diretório dos scripts Python
WORKDIR /${GIT_REPO_NAME}/scripts/python

RUN pip3 install --upgrade pi

# Instala as dependências do Python
RUN pip3 install --no-cache-dir -r requirements.txt

# Executa os scripts Python
RUN python3 create_tables.py && \
    python3 populate_tables.py

# Comando para executar o .jar
CMD ["java", "-jar", "/${GIT_REPO_NAME}/app/backend/target/seu_projeto.jar"]
