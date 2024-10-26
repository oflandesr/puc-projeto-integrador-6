# Use a imagem base do Alpine
FROM alpine:latest

# Instale pacotes necessários
RUN apk update && \
    apk add --no-cache openjdk17 mysql mysql-client && \
    rm -rf /var/cache/apk/*

# Crie um usuário e grupo para o MySQL
#RUN adduser -S mysql -G mysql

# Defina a variável de ambiente JAVA_HOME
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk
ENV PATH="$JAVA_HOME/bin:$PATH"

# Crie o diretório para o MySQL e ajuste permissões
RUN mkdir /var/lib/mysql && \
    chown -R mysql:mysql /var/lib/mysql

# Exponha a porta padrão do MySQL
EXPOSE 3306

# Mude para o usuário mysql
USER mysql

# Comando para iniciar o MySQL
CMD ["mysqld"]
