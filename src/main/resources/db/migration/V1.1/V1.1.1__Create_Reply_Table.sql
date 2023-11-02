CREATE TABLE IF NOT EXISTS reply (
                       reply_id serial PRIMARY KEY,
                       user_id BIGINT NOT NULL,
                       answer_id BIGINT NOT NULL,
                       description TEXT NOT NULL UNIQUE,
                       created TIMESTAMP NOT NULL,
                       FOREIGN KEY (user_id) REFERENCES "user"(id),
                       FOREIGN KEY (answer_id) REFERENCES answer(answer_id)
);
