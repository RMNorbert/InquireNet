CREATE TABLE answer (
                        answer_id serial PRIMARY KEY,
                        user_id BIGINT NOT NULL,
                        question_id BIGINT NOT NULL,
                        description TEXT NOT NULL,
                        created TIMESTAMP NOT NULL,
                        vote VARCHAR NOT NULL,
                        FOREIGN KEY (user_id) REFERENCES "user"(id),
                        FOREIGN KEY (question_id) REFERENCES question(question_id)
);
