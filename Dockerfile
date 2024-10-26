# Usando a imagem Alpine como base
FROM alpine:latest


# Instala o bash para execução do builder
RUN apk add --no-cache bash

# Copia o script builder para o container
COPY ./builder.sh /builder.sh

# Torna o script executável
RUN chmod +x /builder.sh

# Exponha a porta do MySQL
EXPOSE 3306

# Exponha a porta da sua aplicação (ajuste conforme necessário)
EXPOSE 8080

# Define o entrypoint
ENTRYPOINT ["/builder.sh"]
