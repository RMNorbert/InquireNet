CREATE TABLE IF NOT EXISTS loginHistory (
                        history_id serial PRIMARY KEY,
                        time TIMESTAMP NOT NULL,
                        user_id BIGINT NOT NULL,
                        ip_address VARCHAR NOT NULL,
                        user_agent VARCHAR NOT NULL,
                        FOREIGN KEY (user_id) REFERENCES "user"(id)
);
