CREATE TABLE question (
                          question_id serial PRIMARY KEY,
                          user_id BIGINT,
                          title VARCHAR(200) NOT NULL UNIQUE,
                          description TEXT,
                          created TIMESTAMP NOT NULL,
                          FOREIGN KEY (user_id) REFERENCES "user"(id)
);
