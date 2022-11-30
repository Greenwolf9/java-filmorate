package ru.yandex.practicum.filmorate.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class RatingMpaImpl {

    private final JdbcTemplate jdbcTemplate;

    public RatingMpaImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Mpa get(int id) {
        String sql = "select * from mpa_rating where MPA_ID = ?";
        return jdbcTemplate.queryForObject(sql, RatingMpaImpl::makeRatingMpa, id);
    }

    public List<Mpa> getAll() {
        String sql = "select * from mpa_rating";
        final List<Mpa> mpa = jdbcTemplate.query(sql, RatingMpaImpl::makeRatingMpa);
        return mpa;
    }

    protected static Mpa makeRatingMpa(ResultSet rs, int rowNum) throws SQLException {
        return new Mpa(rs.getInt("mpa_id"), rs.getString("mpa_name"));
    }
}
