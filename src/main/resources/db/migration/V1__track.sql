CREATE TABLE artist (
                         id               SERIAL PRIMARY KEY,
                         name             VARCHAR(255) NOT NULL,
                         country          VARCHAR(32),
                         status           VARCHAR(20),
                         created_at  TIMESTAMPTZ   NOT NULL DEFAULT now(),
                         updated_at  TIMESTAMPTZ,
                         created_by  VARCHAR(64),
                         updated_by  VARCHAR(64)
);

