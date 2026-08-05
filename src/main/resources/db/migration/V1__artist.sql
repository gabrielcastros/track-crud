CREATE TABLE artist (
                         id               BIGSERIAL PRIMARY KEY,
                         name             VARCHAR(255) NOT NULL,
                         country          VARCHAR(128) NOT NULL,
                         status           VARCHAR(20) NOT NULL,
                         created_at       TIMESTAMPTZ   NOT NULL DEFAULT now(),
                         updated_at       TIMESTAMPTZ,
                         created_by       VARCHAR(64) NOT NULL,
                         updated_by       VARCHAR(64)
);

