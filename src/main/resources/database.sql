CREATE TABLE chat(chat_id serial PRIMARY KEY , user_id BIGINT NOT NULL, title VARCHAR NOT NULL, role VARCHAR NOT NULL, content TEXT NOT NULL);
CREATE TABLE question(question_id serial PRIMARY KEY ,user_id BIGINT,  title VARCHAR(200) NOT NULL UNIQUE, description TEXT, created TIMESTAMP NOT NULL);
CREATE TABLE answer(answer_id serial PRIMARY KEY ,user_id BIGINT NOT NULL ,question_id BIGINT NOT NULL , description TEXT NOT NULL, created TIMESTAMP NOT NULL, vote VARCHAR NOT NULL);
CREATE TABLE "user"(id serial PRIMARY KEY , role VARCHAR(50), username VARCHAR(50) NOT NULL UNIQUE, password VARCHAR NOT NULL, registration_date TIMESTAMP );
CREATE TABLE reply(reply_id serial PRIMARY KEY , user_id BIGINT NOT NULL,answer_id BIGINT NOT NULL , description TEXT NOT NULL UNIQUE, created TIMESTAMP NOT NULL);

INSERT INTO question(question_id, user_id, title, description, created) VALUES (1,1,'Why dont scientists trust atoms?','Im curious', NOW());
INSERT INTO question(question_id, user_id, title, description, created) VALUES (2,2,'Why did the scarecrow win an award?','', NOW());
INSERT INTO question(question_id, user_id, title, description, created) VALUES (3,3,'Why don''t skeletons fight each other?','Why should they?', NOW());
INSERT INTO answer(answer_id, user_id, question_id, description, created, vote) VALUES (1,1,1,'Because they make up everything!', NOW(), 'unvoted');
INSERT INTO answer(answer_id, user_id, question_id, description, created, vote) VALUES (2,2,2,'Because he was outstanding in his field!', NOW(), 'unvoted');
INSERT INTO answer(answer_id, user_id, question_id, description, created, vote) VALUES (3,3,3,' They dont have the guts!', NOW(), 'unvoted');
INSERT INTO "user" (id , role, username, password, registration_date) VALUES (1,'USER','user','user',NOW());
INSERT INTO "user" (id , role, username, password, registration_date) VALUES (2,'USER','armi','mi',NOW());
INSERT INTO "user" (id , role, username, password, registration_date) VALUES (3,'USER','rmi','ami',NOW());
INSERT INTO chat(chat_id, user_id, title, role, content) VALUES (1, 1, 'Share your time', 'user', 'Share your time');
INSERT INTO chat(chat_id, user_id, title, role, content) VALUES (2, 1, 'Share your time', 'ai', 'I dont have time');
