# Usando a imagem Alpine como base
FROM openjdk:17.0.1-jdk-slim

# Instala as dependências necessárias
RUN apt-get update && \
apt-get install -y python3 python3-pip python3-venv default-mysql-client git maven && \
rm -rf /var/lib/apt/lists/*

# Copia o script entrypoint
COPY ./builder.sh /app/builder.sh
RUN chmod +x /app/builder.sh

# Exponha a porta do MySQL
EXPOSE 3306

# Exponha a porta da sua aplicação (ajuste conforme necessário)
EXPOSE 8080

# Define o entrypoint
ENTRYPOINT ["/app/builder.sh"]
