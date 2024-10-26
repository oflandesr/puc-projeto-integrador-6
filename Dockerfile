FROM mysql:oraclelinux9

# Copia o script entrypoint
COPY builder.sh builder.sh
RUN chmod +x builder.sh

# Exponha a porta do MySQL
EXPOSE 3306

# Exponha a porta da sua aplicação (ajuste conforme necessário)
EXPOSE 8080

# Define o entrypoint
ENTRYPOINT ["/builder.sh"]
