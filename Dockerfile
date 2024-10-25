FROM openjdk:17-jdk-slim

# Instala o Git, Python e Maven
RUN apt-get update && \
    apt-get install -y git python3 python3-pip maven && \
    rm -rf /var/lib/apt/lists/*

# Copia o script entrypoint
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

# Define o entrypoint
ENTRYPOINT ["/entrypoint.sh"]
