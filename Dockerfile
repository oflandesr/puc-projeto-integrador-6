FROM debian:bullseye-slim

# Instala o Git, Python, Maven e MySQL
RUN apt-get update && \
    apt-get install -y git python3 python3-pip maven mysql-server && \
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
