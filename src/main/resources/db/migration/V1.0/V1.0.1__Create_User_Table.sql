CREATE TABLE "user" (
                        id serial PRIMARY KEY,
                        role VARCHAR(50),
                        username VARCHAR(50) NOT NULL UNIQUE,
                        password VARCHAR NOT NULL,
                        registration_date TIMESTAMP
);
