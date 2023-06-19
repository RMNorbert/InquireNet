CREATE TABLE question(question_id serial PRIMARY KEY ,user_id INT,  title VARCHAR(200) NOT NULL, description TEXT, created TIMESTAMP NOT NULL);
CREATE TABLE answer(answer_id serial PRIMARY KEY ,question_id INT NOT NULL , description TEXT NOT NULL, created TIMESTAMP NOT NULL, vote VARCHAR NOT NULL);
CREATE TABLE "user"(id serial PRIMARY KEY , status VARCHAR(50), name VARCHAR(50) NOT NULL , password VARCHAR NOT NULL, registration_date TIMESTAMP, number_of_questions INT NOT NUll, number_of_answers INT NOT NULL);
CREATE TABLE reply(reply_id serial PRIMARY KEY ,answer_id INT NOT NULL , description TEXT NOT NULL, created TIMESTAMP NOT NULL);

INSERT INTO question(question_id,user_id,title,description,created) VALUES (1,1,'Why dont scientists trust atoms?','Im curious', NOW());
INSERT INTO question(question_id,user_id,title,description,created) VALUES (2,2,'Why did the scarecrow win an award?','', NOW());
INSERT INTO question(question_id,user_id,title,description,created) VALUES (3,3,'Why don''t skeletons fight each other?','Why should they?', NOW());

INSERT INTO answer(answer_id,question_id,description,created, vote) VALUES (1,1,'Because they make up everything!', NOW(), 'unvoted');
INSERT INTO answer(answer_id,question_id,description,created, vote) VALUES (2,2,'Because he was outstanding in his field!', NOW(), 'unvoted');
INSERT INTO answer(answer_id,question_id,description,created, vote) VALUES (3,3,' They dont have the guts!', NOW(), 'unvoted');
INSERT INTO "user" (id ,status,name,password,registration_date,number_of_questions,number_of_answers) VALUES (1,'User','user','user',NOW(),0,0);
INSERT INTO "user" (id ,status,name,password,registration_date,number_of_questions,number_of_answers) VALUES (2,'User','armi','mi',NOW(),0,0);
INSERT INTO "user" (id ,status,name,password,registration_date,number_of_questions,number_of_answers) VALUES (3,'User','rmi','ami',NOW(),0,0);
