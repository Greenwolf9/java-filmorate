CREATE TABLE IF NOT EXISTS users(
    user_id INT NOT NULL AUTO_INCREMENT,
    email VARCHAR(50),
    login VARCHAR(50),
    name VARCHAR(50),
    birthday DATE,
    primary key (user_id)
    );
CREATE TABLE IF NOT EXISTS mpa_rating(
    mpa_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    mpa_name VARCHAR(50)
);
CREATE TABLE IF NOT EXISTS films(
    film_id INTEGER NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(250),
    release_date DATE,
    duration INTEGER,
    rate INTEGER,
    mpa_id INTEGER NOT NULL REFERENCES mpa_rating(mpa_id),
    primary key(film_id)
    );

CREATE TABLE IF NOT EXISTS friends(
    user_id INTEGER REFERENCES users,
    friend_id INTEGER REFERENCES users,
    PRIMARY KEY (user_id, friend_id)
);

CREATE TABLE IF NOT EXISTS likes(
    user_id INTEGER REFERENCES users,
    film_id INTEGER REFERENCES films,
    PRIMARY KEY (user_id, film_id)
);
CREATE TABLE IF NOT EXISTS genres(
    genre_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(50)
);
CREATE TABLE IF NOT EXISTS film_genres(
    film_id INTEGER REFERENCES films,
    genre_id INTEGER REFERENCES genres,
    PRIMARY KEY (film_id, genre_id)
);
