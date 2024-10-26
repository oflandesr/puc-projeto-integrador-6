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
    mysql_install_db --user=root --datadir=/var/lib/mysql
    
    # Cria o arquivo de configuração do MySQL se não existir
    if [ ! -f /etc/my.cnf ]; then
        echo "[mysqld]" > /etc/my.cnf
    fi

    # Adiciona a configuração do bind-address
    if ! grep -q "bind-address" /etc/my.cnf; then
        echo "bind-address = 0.0.0.0" >> /etc/my.cnf
        echo "bind-address adicionado ao arquivo de configuração do MySQL."
    fi

    # Inicia o MySQL em segundo plano
    mysqld_safe --datadir=/var/lib/mysql &
    sleep 5
    # Aguarda o MySQL iniciar
    echo "Aguardando MySQL iniciar..."
    for i in {1..10}; do
        if mysql -h "${MYSQL_HOST}" -u root -e "SELECT 1;" --silent; then
            echo "MySQL iniciado com sucesso."
            break
        else
            echo "Esperando MySQL iniciar ($i/10)..."
            sleep 2
        fi
    done

    # Configura o MySQL (exemplo de criação de banco e usuário)
    mysql -u root <<EOF
    CREATE DATABASE IF NOT EXISTS ${MYSQL_DATABASE};
    CREATE USER IF NOT EXISTS '${MYSQL_USER}'@'${MYSQL_HOST}' IDENTIFIED BY '${MYSQL_PASSWORD}';
    GRANT ALL PRIVILEGES ON ${MYSQL_DATABASE}.* TO '${MYSQL_USER}'@'${MYSQL_HOST}';
    FLUSH PRIVILEGES;
EOF
    
    echo "MySQL configurado e inicializado."
}

# Função para testar a conexão com o MySQL
test_mysql_connection() {
    echo "Testando conexão com o MySQL..."
    if mysql -h "${MYSQL_HOST}" -u"${MYSQL_USER}" -p"${MYSQL_PASSWORD}" -e "USE ${MYSQL_DATABASE};" >/dev/null 2>&1; then
        echo "Conexão com o MySQL foi bem-sucedida."
    else
        echo "Erro: Não foi possível conectar ao MySQL com as credenciais fornecidas."
        exit 1
    fi
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

    # Cria e ativa o ambiente virtual Python
    echo "Configurando ambiente Python..."
    python3 -m venv /venv
    source /venv/bin/activate

    echo "Executando scripts..."
    pip install --no-cache-dir -r "/${GIT_REPO_NAME}/scripts/python/requirements.txt"
    python3 "/${GIT_REPO_NAME}/scripts/python/create_tables.py"
    python3 "/${GIT_REPO_NAME}/scripts/python/etl.py"

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
    install_and_configure_packages
    install_and_configure_mysql
    test_mysql_connection
    update_repository
    #setup_database_and_build
} || handle_error

# Manter o contêiner em execução
tail -f /dev/null