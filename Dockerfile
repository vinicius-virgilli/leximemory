FROM mysql:8.0.35
ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=leximemorydb

COPY backup.sql /docker-entrypoint-initdb.d/
