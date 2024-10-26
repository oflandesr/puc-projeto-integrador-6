#!/bin/bash
set -e

# Função para instalar OpenJDK 17 e bibliotecas necessárias
install_dependencies() {
    echo "Instalando OpenJDK 17 e bibliotecas necessárias..."

    # Atualiza a lista de pacotes e instala as bibliotecas
    apt-get update && \
    apt-get install -y openjdk-17-jdk git maven && apt-get clean
    pip3 install --upgrade pi
    rm -rf /var/lib/apt/lists/*

    # Define JAVA_HOME
    export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
    echo "JAVA_HOME setado para $JAVA_HOME"

    echo "Instalação concluída."
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

# Função para executar scripts Python para configuração do banco de dados
setup_database() {
    echo "Executando scripts Python para setup do banco de dados..."
    pip3 install --no-cache-dir -r "/${GIT_REPO_NAME}/scripts/python/requirements.txt"
    cd "/${GIT_REPO_NAME}/scripts/python" || exit
    python3 "/${GIT_REPO_NAME}/scripts/pythoncreate_tables.py"
    python3 "/${GIT_REPO_NAME}/scripts/pythonpopulate_tables.py"
}

# Função para compilar e iniciar a aplicação Java
build_and_run_java_app() {
    echo "Compilando o projeto Java..."
    cd "/${GIT_REPO_NAME}/app/backend" || exit
    mvn clean package -DskipTests

    echo "Iniciando a aplicação Java..."
    exec java -jar "/${GIT_REPO_NAME}/app/backend/target/ProjetoIntegradorVI*.jar"
}

# Função para tratar erros
handle_error() {
    echo "Ocorreu um erro na execução do script."
    exit 1
}

# Chamadas das funções com tratamento de erro
{
    install_dependencies
    #update_repository
    #setup_database
    #build_and_run_java_app
} || handle_error
