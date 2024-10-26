#!/bin/bash
set -e

# Função para instalar pacotes e configurar o ambiente
install_and_configure_packages() {
    echo "Instalando pacotes e configurando ambiente..."
    apk add --no-cache python3 py3-pip py3-virtualenv openjdk17 mysql mysql-client git maven

    # Configura JAVA_HOME
    export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
    echo "JAVA_HOME setado para $JAVA_HOME"

    echo "Instalação e configuração do ambiente concluída."
}

# Função para instalar e configurar o MySQL
install_and_configure_mysql() {
    echo "Instalando e configurando MySQL..."

    # Inicializa o diretório de dados
    mysql_install_db --user=mysql --datadir=/var/lib/mysql

    # Inicia o MySQL em segundo plano
    mysqld_safe --datadir=/var/lib/mysql &
    
    # Aguarda o MySQL iniciar
    echo "Aguardando MySQL iniciar..."
    for i in {1..10}; do
        if mysqladmin ping -h "localhost" --silent; then
            echo "MySQL iniciado com sucesso."
            break
        else
            echo "Esperando MySQL iniciar ($i/10)..."
            sleep 2
        fi
    done

    # Configura o MySQL (exemplo de criação de banco e usuário)
    if mysql -u root -e "ALTER USER 'root'@'localhost' IDENTIFIED BY '${MYSQL_ROOT_PASSWORD}';"; then
        echo "Senha do root configurada."
    else
        echo "Erro ao definir a senha root. Verifique se o MySQL foi inicializado corretamente."
        exit 1
    fi
    
    # Configura banco de dados e usuário
    mysql -u root --password="${MYSQL_ROOT_PASSWORD}" <<EOF
CREATE DATABASE IF NOT EXISTS ${MYSQL_DATABASE};
CREATE USER IF NOT EXISTS '${MYSQL_USER}'@'%' IDENTIFIED BY '${MYSQL_PASSWORD}';
GRANT ALL PRIVILEGES ON ${MYSQL_DATABASE}.* TO '${MYSQL_USER}'@'%';
FLUSH PRIVILEGES;
EOF

    echo "MySQL configurado e inicializado."
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
    echo "Configurando ambiente Python e executando scripts de setup..."

    # Cria e ativa o ambiente virtual Python
    python3 -m venv /app/venv
    source /app/venv/bin/activate

    echo "Instalando dependências do Python..."
    pip install --no-cache-dir -r "/${GIT_REPO_NAME}/scripts/python/requirements.txt"

    echo "Executando scripts para criar tabelas e ETL..."
    python3 "/${GIT_REPO_NAME}/scripts/python/create_tables.py"
    python3 "/${GIT_REPO_NAME}/scripts/python/etl.py"

    echo "Compilando o projeto Java..."
    cd "/${GIT_REPO_NAME}/app/backend" || exit
    mvn clean package -DskipTests

    echo "Preparado para iniciar a aplicação Java."
}

# Função para tratar erros
handle_error() {
    echo "Ocorreu um erro na execução do script."
    exit 1
}

# Chamadas das funções com tratamento de erro
{
    install_and_configure_packages
    install_and_configure_mysql
    update_repository
    setup_database_and_build
} || handle_error
