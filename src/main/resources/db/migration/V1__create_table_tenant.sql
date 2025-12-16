CREATE TABLE tenant (
                        id BIGSERIAL PRIMARY KEY,
                        public_id UUID NOT NULL UNIQUE,
                        name VARCHAR(150) NOT NULL,
                        slug VARCHAR(100) NOT NULL UNIQUE,
                        status VARCHAR(30) NOT NULL,
                        created_at TIMESTAMP NOT NULL,
                        updated_at TIMESTAMP NOT NULL
);

