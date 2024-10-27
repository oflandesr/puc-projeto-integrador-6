#!/bin/bash
set -e

# Função principal
main() {
    echo "Verificando o repositório..."

    if [ -d "/${GIT_REPO_NAME}/app/" ]; then
        echo "Diretório do repositório encontrado. Atualizando com git pull..."
        cd "/${GIT_REPO_NAME}" || exit 1
        
        # Captura a saída do comando git pull
        output=$(git pull origin "${GIT_BRANCH}")
        echo "$output"

        # Verifica se a saída contém a mensagem de atualização
        if [[ "$output" == *"up to date"* ]]; then
            echo "O repositório já está atualizado."
        else
            echo "Atualizações encontradas. Executando Maven..."
            cd "/${GIT_REPO_NAME}/app/backend" || exit 1
            mvn clean package -DskipTests
        fi
    else
        echo "Clonando o repositório..."
        git clone -b "${GIT_BRANCH}" "https://${GIT_USER}:${GIT_TOKEN}@github.com/${GIT_USER}/${GIT_REPO_NAME}.git" "/${GIT_REPO_NAME}"
        
        # Configura o ambiente Python
        echo "Preparando ambiente Python..."
        setup_python_env

        # Executa Maven após o clone
        echo "Executando Maven..."
        cd "/${GIT_REPO_NAME}/app/backend" || exit 1
        mvn clean package -DskipTests
    fi

    upsert_database   # Chama a função para popular o MySQL
    application_run  # Chama a função para executar a aplicação
}

# Função para configurar o ambiente Python
setup_python_env() {
    if command -v python3 &> /dev/null; then
        if [ ! -d "/venv" ]; then
            python3 -m venv /venv
            echo "Ambiente virtual Python criado."
        else
            echo "Ambiente virtual Python já existe. Pulando criação."
        fi
        source /venv/bin/activate
        pip install --no-cache-dir -r "/${GIT_REPO_NAME}/scripts/python/requirements.txt"
    else
        echo "Python3 não está disponível. Instale o Python3 e tente novamente."
        exit 1
    fi
}

# Função para aguardar a conexão com o MySQL
wait_for_mysql() {
    echo "Aguardando conexão com o MySQL..."
    MAX_ATTEMPTS=12
    attempts=0
    until mysql -h "${MYSQL_HOST}" -u"${MYSQL_USER}" -p"${MYSQL_PASSWORD}" -e "SELECT 1;" > /dev/null 2>&1; do
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
upsert_database() {
    echo "Iniciando operações com o MySQL..."
    wait_for_mysql  # Aguarda conexão antes de popular

    echo "Executando scripts Python para o banco de dados..."
    
    # Ativa o ambiente virtual se existir
    setup_python_env

    if [ ! -f "/${GIT_REPO_NAME}/.mysql_initialized" ]; then
        echo "Criando tabelas e usuários..."
        python3 "/${GIT_REPO_NAME}/scripts/python/create_tables.py"
        touch "/${GIT_REPO_NAME}/.mysql_initialized"
    fi

    echo "Atualizando os dados..."
    python3 "/${GIT_REPO_NAME}/scripts/python/etl.py"  # Sempre executa o ETL
    echo "Operações com o banco de dados finalizadas"
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
