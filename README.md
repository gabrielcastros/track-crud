# TrackCrud

CRUD de artistas. Java 25, Spring Boot 4.1, Postgres, Flyway, Testcontainers.

CRUD básico, escrito na mão, sem IA no código pra treinar a base.

## Rodar

Só precisa de Docker.

```bash
cp .env.example .env
docker compose up --build
```

Testes:

```bash
./mvnw verify
```

Precisa do Docker rodando porque os testes de integração sobem um Postgres de verdade com Testcontainers.

## Endpoints

Tudo em `/api/artists`.

- `POST /api/artists` cria. Volta 201 com o header `Location` apontando pro artista novo.
- `GET /api/artists/{id}` busca um. 404 se não existe ou se está inativo.
- `GET /api/artists?q=&country=&page=&size=&sort=` lista paginado, filtrando por nome e país.
- `PUT /api/artists/{id}` atualiza nome e país.
- `DELETE /api/artists/{id}` inativa. 204. Se já estava inativo, 404.
