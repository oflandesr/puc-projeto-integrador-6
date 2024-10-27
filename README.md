# projeto-integrador-6
Repositório destinado a publicação dos artefatos para o Projeto Integrador 6, no segundo semestre  PUCC, 2024.

# Use o comando para subir a aplicação.
sudo docker compose down && sudo docker compose up --build

# Use o comando para reiniciar os volumes (Limpar o banco de dados e recriar)
sudo docker compose down -v && sudo docker compose down && sudo docker compose up --build

# Use o comando para reiniciar tudo do zero.
sudo docker compose down -v && sudo docker system prune -a --volumes && sudo docker compose down && sudo docker compose up --build

# O uso do `sudo` nos comandos depende de como o docker foi instalado