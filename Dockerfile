# Usando a imagem Alpine como base
FROM alpine:latest

# Instala dependências, incluindo pip3 e venv
RUN apk add --no-cache bash sudo

# Copia o script entrypoint e define permissões de execução
COPY ./builder.sh /builder.sh
RUN chmod +x /builder.sh

# Exponha a porta do MySQL
EXPOSE 3306

# Exponha a porta da sua aplicação (ajuste conforme necessário)
EXPOSE 8080

# Define o entrypoint para rodar dentro do ambiente virtual
ENTRYPOINT ["/builder.sh"]
