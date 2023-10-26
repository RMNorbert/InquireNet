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
INSERT INTO "user" VALUES (1, 'USER', 'username', 'password', NOW()),
                          (2, 'USER', 'username2', 'password', NOW());
INSERT INTO question (question_id, user_id, title, description, created)
VALUES
    (1, 1, 'title 1', 'description 1', NOW()),
    (2, 2, 'title 2', 'description 2', NOW());

INSERT INTO answer (answer_id, user_id, question_id, description, created, vote)
VALUES
    (1, 1, 1, 'answer description 1', NOW(), 'voted'),
    (2, 2, 2, 'answer description 2', NOW(), 'unvoted');

INSERT INTO chat (chat_id, user_id, title, role, content)
VALUES
    (1, 1, 'chat 1', 'user', 'content 1'),
    (2, 2, 'chat 2', 'user', 'content 2');
INSERT INTO reply
VALUES
    (1, 1, 1, 'reply 1', now());
