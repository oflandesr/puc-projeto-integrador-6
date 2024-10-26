FROM debian:bullseye-slim

# Instala o Git, Python, Maven e MySQL

# Copia o script entrypoint
COPY ./builder.sh /app/builder.sh
RUN chmod +x /app/builder.sh

# Exponha a porta do MySQL
EXPOSE 3306

# Exponha a porta da sua aplicação (ajuste conforme necessário)
EXPOSE 8080

# Define o entrypoint
ENTRYPOINT ["/app/builder.sh"]
