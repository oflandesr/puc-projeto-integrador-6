#!/bin/bash
set -e

install_and_configure_java() {
    echo "Instalando Maven e Java..."
    
    apk add --no-cache openjdk17 maven

    export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
    echo "JAVA_HOME setado para $JAVA_HOME"

    echo "Instalação concluída."
}

# Função para instalar e configurar o MySQL
install_and_configure_mysql() {
    echo "Instalando MySQL..."
    
    # Instala o MySQL e cria o diretório de dados
    apk add --no-cache mysql mysql-client
    mysql_install_db --user=mysql --datadir=/var/lib/mysql

    # Cria o usuário e o banco de dados
    echo "Configurando o MySQL..."
    mysqld_safe --datadir=/var/lib/mysql &

    # Aguarda o MySQL inicializar
    sleep 5

    # Configura o MySQL (exemplo de criação de banco e usuário)
    mysql -u root <<EOF
    ALTER USER 'root'@'localhost' IDENTIFIED BY '${MYSQL_ROOT_PASSWORD}';
    CREATE DATABASE IF NOT EXISTS ${MYSQL_DATABASE};
    CREATE USER IF NOT EXISTS '${MYSQL_USER}'@'%' IDENTIFIED BY '${MYSQL_PASSWORD}';
    GRANT ALL PRIVILEGES ON ${MYSQL_DATABASE}.* TO '${MYSQL_USER}'@'%';
    FLUSH PRIVILEGES;
EOF
    
    echo "MySQL inicializado e configurado."
}

# Função para clonar ou atualizar o repositório
update_repository() {
    echo "Verificando o repositório..."
    apk add --no-cache git
    
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
    apk add --no-cache python3 python3-pip
    pip3 install --upgrade pi
    
    cd "/${GIT_REPO_NAME}/scripts/python" || exit
    pip3 install --no-cache-dir -r requirements.txt
    python3 create_tables.py
    python3 etl.py

    echo "Compilando o projeto Java..."
    cd "/${GIT_REPO_NAME}/app/backend" || exit
    mvn clean package -DskipTests

    echo "Iniciando a aplicação Java..."
    exec java -jar /${GIT_REPO_NAME}/app/backend/target/*.jar
}

# Função para tratar erros
handle_error() {
    echo "Ocorreu um erro na execução do script."
    exit 1
}

# Chamadas das funções com tratamento de erro
{
    install_dependencies
    install_and_configure_mysql
    update_repository
    setup_database_and_build
} || handle_error
