package ru.yandex.practicum.filmorate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.*;
import java.sql.Date;
import java.util.*;

@Repository("filmDbStorage")
@Primary
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Film film) {
        String sql = "INSERT INTO films (NAME, DESCRIPTION, RELEASE_DATE, DURATION, RATE, MPA_ID) " +
                "VALUES (?,?,?,?,?,?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"film_id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getRate());
            stmt.setInt(6, film.getMpa().getId());
            return stmt;
        }, keyHolder);
        film.setId(keyHolder.getKey().intValue());
        saveFilmGenreId(film);
    }

    @Override
    public void update(Film film) {
        String sql = "UPDATE films SET NAME=?, DESCRIPTION=?, RELEASE_DATE=?, DURATION=?, RATE=?, MPA_ID=? where FILM_ID =?";
        jdbcTemplate.update(sql, film.getName(),
                film.getDescription(), film.getReleaseDate(), film.getDuration(),
                film.getRate(), film.getMpa().getId(), film.getId());
        saveFilmGenreId(film);
    }

    @Override
    public void delete(Film film) {
        String sql = "DELETE FROM films WHERE FILM_ID = ?";
        jdbcTemplate.update(sql, film.getId());
    }

    @Override
    public List<Film> getAll() {
        String sql = "select * from films as f, MPA_RATING as MR WHERE MR.MPA_ID = f.MPA_ID";
        return jdbcTemplate.query(sql, FilmDbStorage::makeFilm);
    }

    @Override
    public Film get(int id) {
        String sql = "select * from films as f, MPA_RATING as MR WHERE MR.MPA_ID = f.MPA_ID AND FILM_ID = ?";
        return jdbcTemplate.queryForObject(sql, FilmDbStorage::makeFilm, id);
    }

    protected static Film makeFilm(ResultSet rs, int rowNum) throws SQLException {
        return new Film(rs.getInt("film_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDate("release_date").toLocalDate(),
                rs.getInt("duration"),
                rs.getInt("rate"),
                new Mpa(rs.getInt("mpa_id"), rs.getString("mpa_name")));
    }


    private void saveFilmGenreId(Film film) {
        final int filmId = film.getId();
        jdbcTemplate.update("delete from FILM_GENRES where FILM_ID=?", filmId);
        List<Genre> genres = new ArrayList<>(film.getGenres());
        if (genres.isEmpty()) {
            return;
        }
        String sql = "MERGE INTO FILM_GENRES (FILM_ID, GENRE_ID) VALUES ( ?,? )";
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql);

            for (int id = 0; id < genres.size(); id++) {
                stmt.setInt(1, filmId);
                stmt.setInt(2, genres.get(id).getId());
                stmt.executeUpdate();
            }
            return stmt;
        });
    }
}
