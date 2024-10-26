#!/bin/bash
set -e

install_dependencies() {
    echo "Instalando OpenJDK 17, Git, Maven e Python..."

    apt-get update && \
    apt-get install -y openjdk-17-jdk git maven python3 python3-pip && \
    rm -rf /var/lib/apt/lists/*

    export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
    echo "JAVA_HOME setado para $JAVA_HOME"

    echo "Instalação concluída."
}

# Função para instalar e configurar o MySQL
install_and_configure_mysql() {
    echo "Instalando o MySQL..."
    apk update && \
    apk add --no-cache openjdk17 mysql mysql-client && \
    rm -rf /var/cache/apk/*

    echo "Configurando o MySQL..."
    service mysql start

    # Criação do banco de dados e usuário (personalize conforme necessário)
    mysql -e "CREATE DATABASE IF NOT EXISTS ${MYSQL_DATABASE};"
    mysql -e "CREATE USER IF NOT EXISTS '${MYSQL_USER}'@'localhost' IDENTIFIED BY '${MYSQL_PASSWORD}';"
    mysql -e "GRANT ALL PRIVILEGES ON ${MYSQL_DATABASE}.* TO '${MYSQL_USER}'@'localhost';"
    mysql -e "FLUSH PRIVILEGES;"
}

# Função para clonar ou atualizar o repositório
update_repository() {
    echo "Verificando o repositório..."

    if [ -d "/${GIT_REPO_NAME}" ]; then
        echo "Diretório do repositório encontrado. Atualizando com git pull..."
        cd "/${GIT_REPO_NAME}" || exit
        git fetch -a
        git checkout "${GIT_BRANCH}"
        git pull origin "${GIT_BRANCH}"
    else
        echo "Clonando o repositório..."
        git clone -b "${GIT_BRANCH}" "https://${GIT_USER}:${GIT_TOKEN}@github.com/${GIT_USER}/${GIT_REPO_NAME}.git" "/${GIT_REPO_NAME}"
    fi
}

# Função para configurar o banco de dados e compilar o projeto Java
setup_database_and_build() {
    echo "Executando scripts Python para setup do banco de dados..."
    cd "/${GIT_REPO_NAME}/scripts/python" || exit
    python3 create_tables.py
    python3 populate_tables.py

    echo "Compilando o projeto Java..."
    cd "/${GIT_REPO_NAME}/app/backend" || exit
    mvn clean package -DskipTests

    echo "Iniciando a aplicação Java..."
    exec java -jar "/${GIT_REPO_NAME}/app/backend/target/seu_projeto.jar"
}

# Função para tratar erros
handle_error() {
    echo "Ocorreu um erro na execução do script."
    exit 1
}

# Chamadas das funções com tratamento de erro
{
    install_dependencies
    #install_and_configure_mysql
    #update_repository
    #setup_database_and_build
} || handle_error
