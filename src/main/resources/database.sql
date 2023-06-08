CREATE TABLE question(question_id serial PRIMARY KEY ,user_id INT,  title VARCHAR(100) NOT NULL, description TEXT, created TIMESTAMP NOT NULL);
CREATE TABLE answer(answer_id serial PRIMARY KEY ,question_id INT NOT NULL , description TEXT NOT NULL, created TIMESTAMP NOT NULL);
CREATE TABLE "user"(id serial PRIMARY KEY , status VARCHAR(50), name VARCHAR(50) NOT NULL , password VARCHAR NOT NULL, registration_date TIMESTAMP, number_of_questions INT NOT NUll, number_of_answers INT NOT NULL);

INSERT INTO question(question_id,user_id,title,description,created) VALUES (1,1,'This','This is', NOW());
INSERT INTO question(question_id,user_id,title,description,created) VALUES (2,2,'Thisis','This is', NOW());
INSERT INTO question(question_id,user_id,title,description,created) VALUES (3,3,'Th','This is', NOW());

INSERT INTO answer(answer_id,question_id,description,created) VALUES (1,1,'This', NOW());
INSERT INTO answer(answer_id,question_id,description,created) VALUES (2,2,'This', NOW());
INSERT INTO answer(answer_id,question_id,description,created) VALUES (3,3,'This', NOW());
INSERT INTO "user" (id ,status,name,password,registration_date,number_of_questions,number_of_answers) VALUES (1,'Boss','akarmi','akarmi',NOW(),0,0);
INSERT INTO "user" (id ,status,name,password,registration_date,number_of_questions,number_of_answers) VALUES (2,'Boss','armi','mi',NOW(),0,0);
INSERT INTO "user" (id ,status,name,password,registration_date,number_of_questions,number_of_answers) VALUES (3,'Boss','rmi','ami',NOW(),0,0);
