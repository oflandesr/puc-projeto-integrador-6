# entrypoint.sh
#!/bin/bash
set -e

# Argumentos do script
GIT_BRANCH=$1
GIT_REPO_NAME=$2
GIT_USER=$3
GIT_TOKEN=$4

# Verifica se o repositório já foi clonado
if [ -d "/${GIT_REPO_NAME}" ]; then
    echo "Diretório do repositório encontrado. Atualizando com git pull..."
    cd "/${GIT_REPO_NAME}"
    git fetch -a
    git checkout "${GIT_BRANCH}"
    git pull origin "${GIT_BRANCH}"
else
    echo "Clonando o repositório..."
    git clone -b "${GIT_BRANCH}" "https://${GIT_USER}:${GIT_TOKEN}@github.com/${GIT_USER}/${GIT_REPO_NAME}.git" "/${GIT_REPO_NAME}"
fi

# Executa os scripts Python para setup do banco de dados
echo "Executando scripts Python para setup do banco de dados..."
cd "/${GIT_REPO_NAME}/scripts/python"
python3 create_tables.py
python3 populate_tables.py

# Compila o projeto Java com Maven
echo "Compilando o projeto Java..."
cd "/${GIT_REPO_NAME}/app/backend"
mvn clean package -DskipTests

# Executa o .jar da aplicação Java
echo "Iniciando a aplicação Java..."
exec java -jar "/${GIT_REPO_NAME}/app/backend/target/seu_projeto.jar"