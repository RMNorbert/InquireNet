INSERT INTO chat (user_id, title, role, content)
VALUES (1, 'Chat 1', 'admin', 'Content of Chat 1'),
       (2, 'Chat 2', 'user', 'Content of Chat 2'),
       (1, 'Chat 3', 'user', 'Content of Chat 3')
ON CONFLICT DO NOTHING;
