INSERT INTO "user" (role, username, password, registration_date)
VALUES ('ADMIN', 'admin_user', '$2a$10$Fpij6/vEmDvJD/vTUp0EqOTjbpDKvTLWpE1Yw91wvCd.AiFQa0Fe.', '2023-10-11 10:00:00'),
       ('USER', 'regular_user', '$2a$10$Fpij6/vEmDvJD/vTUp0EqOTjbpDKvTLWpE1Yw91wvCd.AiFQa0Fe.', '2023-10-11 11:00:00'),
       ('USER', 'another_user', '$2a$10$Fpij6/vEmDvJD/vTUp0EqOTjbpDKvTLWpE1Yw91wvCd.AiFQa0Fe.', '2023-10-11 12:00:00'),
       ('USER', 'another', '$2a$10$Fpij6/vEmDvJD/vTUp0EqOTjbpDKvTLWpE1Yw91wvCd.AiFQa0Fe.', '2023-10-11 12:00:00'),
       ('USER', 'user', '$2a$10$Fpij6/vEmDvJD/vTUp0EqOTjbpDKvTLWpE1Yw91wvCd.AiFQa0Fe.', '2023-10-11 12:00:00')
ON CONFLICT DO NOTHING;

