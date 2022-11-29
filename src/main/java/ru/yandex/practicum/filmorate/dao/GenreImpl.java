package ru.yandex.practicum.filmorate.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Component
public class GenreImpl {

    private final JdbcTemplate jdbcTemplate;

    public GenreImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Genre get(int id) {
        String sql = "select * from GENRES where GENRE_ID = ?";
        return jdbcTemplate.queryForObject(sql, GenreImpl::makeGenre, id);
    }

    public List<Genre> getAll() {
        String sql = "select * from GENRES";
        final List<Genre> genres = jdbcTemplate.query(sql, GenreImpl::makeGenre);
        return genres;
    }

    public Set<Genre> getGenreByFilmId(int filmId) {
        String sql = "SELECT g.* from FILM_GENRES as FG JOIN GENRES G on G.GENRE_ID = FG.GENRE_ID " +
                "WHERE FG.FILM_ID =? ORDER BY G.GENRE_ID";
        final Set<Genre> genres = new LinkedHashSet<>(jdbcTemplate.query(sql, GenreImpl::makeGenre, filmId));
        return genres;
    }

    protected static Genre makeGenre(ResultSet rs, int rowNum) throws SQLException {
        return new Genre(rs.getInt("genre_id"), rs.getString("name"));
    }
}
