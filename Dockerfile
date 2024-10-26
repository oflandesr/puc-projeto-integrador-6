# Usando a imagem Alpine como base
FROM alpine:latest

# Instala as dependências necessárias
RUN apk add --no-cache python3 py3-pip openjdk17 mysql mysql-client bash maven

# Atualiza o pip e instala a biblioteca 'pi'
# RUN pip3 install --upgrade pip
# RUN pip3 install --upgrade pi

# Copia o script entrypoint
COPY ./builder.sh /app/builder.sh
RUN chmod +x /app/builder.sh

# Exponha a porta do MySQL
EXPOSE 3306

# Exponha a porta da sua aplicação (ajuste conforme necessário)
EXPOSE 8080

# Define o entrypoint
ENTRYPOINT ["/app/builder.sh"]
