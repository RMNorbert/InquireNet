CREATE TABLE chat (
                      chat_id serial PRIMARY KEY,
                      user_id BIGINT NOT NULL,
                      title VARCHAR NOT NULL,
                      role VARCHAR NOT NULL,
                      content TEXT NOT NULL,
                      FOREIGN KEY (user_id) REFERENCES "user"(id)
);
