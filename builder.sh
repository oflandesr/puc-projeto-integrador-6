#!/bin/bash
set -e

# Função principal
main() {
    echo "Verificando o repositório..."

    if [ -d "/${GIT_REPO_NAME}/.git" ]; then
        echo "Diretório do repositório encontrado. Atualizando com git pull..."
        cd "/${GIT_REPO_NAME}" || exit
        
        # Captura a saída do comando git pull
        output=$(git pull origin "${GIT_BRANCH}")
        echo "$output"

        # Verifica se a saída contém a mensagem de atualização
        if [[ "$output" == *"up to date"* ]]; then
            echo "O repositório já está atualizado."
        else
            echo "Atualizações encontradas. Executando Maven..."
            cd "/${GIT_REPO_NAME}/app/backend" || exit
            mvn clean package -DskipTests
        fi
    else
        echo "Clonando o repositório..."
        git clone -b "${GIT_BRANCH}" "https://${GIT_USER}:${GIT_TOKEN}@github.com/${GIT_USER}/${GIT_REPO_NAME}.git" "/${GIT_REPO_NAME}"
        configure_mysql  # Configura o MySQL apenas no clone
        cd "/${GIT_REPO_NAME}/app/backend" || exit
        mvn clean package -DskipTests  # Compila a aplicação após o clone
    fi

    populate_mysql   # Chama a função para popular o MySQL
    application_run  # Chama a função para executar a aplicação
}

# Função para aguardar a conexão com o MySQL
wait_for_mysql() {
    echo "Aguardando conexão com o MySQL..."
    MAX_ATTEMPTS=12
    attempts=0
    until mysql -h "${MYSQL_HOST}" -uroot -p"${MYSQL_PASSWORD}" -e "SELECT 1;" > /dev/null 2>&1; do
        if (( attempts++ >= MAX_ATTEMPTS )); then
            echo "Não foi possível conectar ao MySQL após $MAX_ATTEMPTS tentativas. Saindo..."
            exit 1
        fi
        echo "MySQL ainda não está acessível. Tentando novamente em 5 segundos..."
        sleep 5
    done
    echo "MySQL está acessível!"
}

# Função para configurar o MySQL
configure_mysql() {
    wait_for_mysql  # Aguarda conexão antes de configurar
    echo "Configurando usuário MYSQL para o backend..."

    mysql -h "${MYSQL_HOST}" -uroot -p"${MYSQL_PASSWORD}" <<EOF
    CREATE DATABASE IF NOT EXISTS ${MYSQL_DATABASE};
    CREATE USER IF NOT EXISTS 'projiv'@'%' IDENTIFIED BY '${MYSQL_USER_PASSWORD}';
    GRANT ALL PRIVILEGES ON ${MYSQL_DATABASE}.* TO 'projiv'@'%';
    FLUSH PRIVILEGES;
EOF

    echo "MySQL configurado e inicializado."
}

# Função para popular o MySQL
populate_mysql() {
    wait_for_mysql  # Aguarda conexão antes de popular

    echo "Executando scripts Python para setup do banco de dados..."
    if [ ! -d "/venv" ]; then
        python3 -m venv /venv
    fi
    source /venv/bin/activate

    echo "Executando scripts..."
    
    if [ ! -f "/${GIT_REPO_NAME}/.mysql_initialized" ]; then
        pip install --no-cache-dir -r "/${GIT_REPO_NAME}/scripts/python/requirements.txt"
        python3 "/${GIT_REPO_NAME}/scripts/python/create_tables.py"
        touch "/${GIT_REPO_NAME}/.mysql_initialized"
    fi

    python3 "/${GIT_REPO_NAME}/scripts/python/etl.py"  # Sempre executa o ETL
}

# Função para compilar e executar a aplicação Java
application_run() {
    echo "Iniciando a aplicação Java..."
    java -jar /${GIT_REPO_NAME}/app/backend/target/*.jar &
}

# Inicia funções ordenadamente
main

# Manter o contêiner em execução
tail -f /dev/null
