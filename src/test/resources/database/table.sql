CREATE TABLE chat (
                      chat_id serial PRIMARY KEY,
                      user_id BIGINT NOT NULL,
                      title VARCHAR NOT NULL,
                      role VARCHAR NOT NULL,
                      content TEXT NOT NULL
);

CREATE TABLE question (
                          question_id serial PRIMARY KEY,
                          user_id BIGINT,
                          title VARCHAR(200) NOT NULL UNIQUE,
                          description TEXT,
                          created TIMESTAMP NOT NULL
);

CREATE TABLE answer (
                        answer_id serial PRIMARY KEY,
                        user_id BIGINT NOT NULL,
                        question_id BIGINT NOT NULL,
                        description TEXT NOT NULL,
                        created TIMESTAMP NOT NULL,
                        vote VARCHAR NOT NULL
);

CREATE TABLE "user" (
                        id serial PRIMARY KEY,
                        role VARCHAR(50),
                        username VARCHAR(50) NOT NULL UNIQUE,
                        password VARCHAR NOT NULL,
                        registration_date TIMESTAMP
);

CREATE TABLE reply (
                       reply_id serial PRIMARY KEY,
                       user_id BIGINT NOT NULL,
                       answer_id BIGINT NOT NULL,
                       description TEXT NOT NULL UNIQUE,
                       created TIMESTAMP NOT NULL
);
