#!/bin/bash

# Passo 1: Atualiza o banco de dados e faz o backup
docker exec leximemory-db mysqldump -u root -proot leximemorydb > backup.sql

# Passo 2: Cria um novo Dockerfile com os dados atualizados
cat > Dockerfile <<EOF
FROM mysql:8.0.35
ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=leximemorydb

COPY backup.sql /docker-entrypoint-initdb.d/
EOF

# Passo 3: Constrói uma nova imagem Docker com os dados atualizados
docker build -t my_mysql_image_with_data:new_data .

# Passo 4: Exporta a nova imagem Docker para um arquivo tar
docker save -o my_mysql_image_with_data_new.tar my_mysql_image_with_data:new_data
