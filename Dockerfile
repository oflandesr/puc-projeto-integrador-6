FROM openjdk:17-jdk-slim

# Copia o script entrypoint
COPY builder.sh /builder.sh
RUN chmod +x /builder.sh

# Exponha a porta da API
EXPOSE 8080

# Define o entrypoint
ENTRYPOINT ["/builder.sh"]
