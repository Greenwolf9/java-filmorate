DELETE FROM FILM_GENRES;
DELETE FROM LIKES;
DELETE FROM FRIENDS;
DELETE FROM USERS;
DELETE FROM FILMS;

ALTER TABLE USERS ALTER COLUMN USER_ID RESTART WITH 1;
ALTER TABLE FILMS ALTER COLUMN FILM_ID RESTART WITH 1;


MERGE INTO MPA_RATING (MPA_ID, MPA_NAME)
    VALUES (1,'G'), (2,'PG'), (3,'PG-13'), (4,'R'), (5,'NC-17');

MERGE INTO GENRES (GENRE_ID, NAME)
    VALUES (1, 'Комедия'), (2, 'Драма'), (3, 'Мультфильм'), (4, 'Триллер'), (5, 'Документальный'), (6, 'Боевик');

-- INSERT INTO films (NAME, DESCRIPTION, RELEASE_DATE, DURATION, RATE, MPA_ID)
-- VALUES('To Kill a Mockingbird', 'based on Harper Lee''s novel of the same name',
--         '1962-12-03', 120, 5, 1), ('Some Like It Hot', 'How to escape from mafia gangsters',
--        '1959-12-03', 120, 6, 2), ('Psycho', 'one of Hitchcock''s best films',
--        '1960-12-03', 120, 8, 3);
-- INSERT INTO users (EMAIL, LOGIN, NAME, BIRTHDAY)
-- VALUES ('frshtomato@gmail.com', 'Kate987', 'Kate', '2000-02-01'),
--     ('gobusters@gmail.com', 'BigBoss', 'Bill', '1987-01-12'),
--     ('omeletteguru@gmail.com', 'Zimbabwe', 'Jonny', '1996-08-06');




