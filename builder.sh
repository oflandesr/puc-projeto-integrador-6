#!/bin/bash
set -e

# Função para clonar ou atualizar o repositório
get_or_update_repository() {
    echo "Verificando o repositório..."

    if [ -d "/${GIT_REPO_NAME}" ]; then
        echo "Diretório do repositório encontrado. Atualizando com git pull..."
        cd "/${GIT_REPO_NAME}" || exit
        git pull origin "${GIT_BRANCH}"
    else
        echo "Clonando o repositório..."
        git clone -b "${GIT_BRANCH}" "https://${GIT_USER}:${GIT_TOKEN}@github.com/${GIT_USER}/${GIT_REPO_NAME}.git" "/${GIT_REPO_NAME}"
    fi
}

# Função configurar o MySQL
configure_mysql() {

    # Testa conexão com banco
    echo "Configurando usuário MYSQL para o backend..."

    # Configura o MySQL (exemplo de criação de banco e usuário)
    mysql -h "${MYSQL_HOST}" -uroot -p"${MYSQL_PASSWORD}" <<EOF
    CREATE DATABASE IF NOT EXISTS ${MYSQL_DATABASE};
EOF
    
    echo "MySQL configurado e inicializado."
}

popule_mysql() {
    echo "Aguardando conexão com o MySQL..."
    until mysql -h "${MYSQL_HOST}" -uroot -p"${MYSQL_PASSWORD}" -e "SELECT 1;" > /dev/null 2>&1; do
        echo "MySQL ainda não está acessível. Tentando novamente em 5 segundos..."
        sleep 5
    done
    echo "MySQL está acessível!"

    echo "Executando scripts Python para setup do banco de dados..."

    # Cria e ativa o ambiente virtual Python
    echo "Configurando ambiente Python..."
    python3 -m venv /venv
    source /venv/bin/activate

    echo "Executando scripts..."
    pip install --no-cache-dir -r "/${GIT_REPO_NAME}/scripts/python/requirements.txt"
    python3 "/${GIT_REPO_NAME}/scripts/python/create_tables.py"
    python3 "/${GIT_REPO_NAME}/scripts/python/etl.py"
}

buidl_and_execute_application(){
    echo "Compilando o projeto Java..."
    cd "/${GIT_REPO_NAME}/app/backend" || exit
    mvn clean package -DskipTests

    echo "Iniciando a aplicação Java..."
    exec java -jar /${GIT_REPO_NAME}/app/backend/target/*.jar
}

# Inicia funções ordenadamente
get_or_update_repository
configure_mysql
popule_mysql
buidl_and_execute_application
# Manter o contêiner em execução
tail -f /dev/null