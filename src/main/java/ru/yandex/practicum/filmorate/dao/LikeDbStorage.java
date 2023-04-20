package ru.yandex.practicum.filmorate.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@Component
public class LikeDbStorage {

    private final JdbcTemplate jdbcTemplate;

    public LikeDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addLike(int userId, int filmId) {
        String sql = "merge into LIKES (user_id, film_id) VALUES ( ?,? )";
        jdbcTemplate.update(sql, userId, filmId);
        updateRateOfFilm(filmId);
    }

    public void removeLike(int userId, int filmId) {
        String sql = "delete from LIKES WHERE user_id=? AND film_id=?";
        jdbcTemplate.update(sql, userId, filmId);
        updateRateOfFilm(filmId);
    }

    public List<Film> getMostPopularFilms(int count) {
        String sql = "select f.*, mr.MPA_NAME from FILMS f JOIN MPA_RATING MR on f.MPA_ID = MR.MPA_ID " +
                "GROUP BY f.FILM_ID ORDER BY f.RATE DESC LIMIT(?)";
        final List<Film> films = jdbcTemplate.query(sql, FilmDbStorage::makeFilm, count);
        return films;
    }

    private void updateRateOfFilm(int filmId) {
        String sqlLikes = "SELECT count(user_id) from LIKES l WHERE l.FILM_ID=?";
        int likes = jdbcTemplate.queryForObject(sqlLikes, new Object[]{filmId}, Integer.class);
        String sql = "UPDATE films SET rate = rate + ? WHERE film_id = ?";
        jdbcTemplate.update(sql, likes, filmId);
    }
}
