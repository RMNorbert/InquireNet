INSERT INTO answer (user_id, question_id, description, created, vote)
VALUES (1, 1, 'Answer to Question 1', '2023-10-11 10:00:00', 'upvote'),
       (2, 1, 'Another Answer to Question 1', '2023-10-11 11:00:00', 'downvote'),
       (1, 2, 'Answer to Question 2', '2023-10-11 12:00:00', 'upvote'),
       (5, 1, 'desc', '2023-10-11 12:00:00', 'upvote')
ON CONFLICT DO NOTHING;
