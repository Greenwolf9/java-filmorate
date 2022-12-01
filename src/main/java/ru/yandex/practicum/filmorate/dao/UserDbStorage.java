package ru.yandex.practicum.filmorate.dao;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DoesntExistException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.*;
import java.util.List;

@Component
@Primary
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(User data) {
        String sql = "INSERT INTO users (email, login, name, birthday) " +
                "VALUES (?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql, new String[]{"USER_ID"});
            ps.setString(1, data.getEmail());
            ps.setString(2, data.getLogin());
            ps.setString(3, data.getName());
            ps.setDate(4, Date.valueOf(data.getBirthday()));
            return ps;
        }, keyHolder);
        data.setId(keyHolder.getKey().intValue());
    }

    @Override
    public void update(User data) {
        String sql = "UPDATE users SET EMAIL = ?, LOGIN = ?, NAME = ?, BIRTHDAY = ? WHERE USER_ID = ?;";
        jdbcTemplate.update(sql, data.getEmail(), data.getLogin(), data.getName(), data.getBirthday(), data.getId());
    }

    @Override
    public void delete(User data) {
        String sql = "DELETE FROM users WHERE USER_ID = ?;";
        jdbcTemplate.update(sql, data.getId());
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, UserDbStorage::makeUser);
    }

    @Override
    @SneakyThrows
    public User get(int id) {
        String sql = "SELECT * FROM users WHERE USER_ID = ?";
        final List<User> users =  jdbcTemplate.query(sql, UserDbStorage::makeUser, id);
        if (users.size() != 1) {
            throw new DoesntExistException("User id " + id + " doesn't exist");
        }
        return users.get(0);
    }

    protected static User makeUser(ResultSet rs, int rowNum) throws SQLException {
        return new User(rs.getInt("user_id"), rs.getString("email"), rs.getString("login"),
                rs.getString("name"), rs.getDate("birthday").toLocalDate());
    }
}
