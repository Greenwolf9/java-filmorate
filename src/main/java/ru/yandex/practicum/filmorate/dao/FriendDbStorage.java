package ru.yandex.practicum.filmorate.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Component
public class FriendDbStorage {
    private final JdbcTemplate jdbcTemplate;

    public FriendDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addToFriend(int userId, int friendsId) {
        String sql = "merge into friends (user_id, friend_id) VALUES (?,?)";
        jdbcTemplate.update(sql, userId, friendsId);
    }

    public void deleteFromFriend(int userId, int friendsId) {
        String sql = "delete from friends WHERE user_id=? AND friend_id = ?";
        jdbcTemplate.update(sql, userId, friendsId);
    }

    public List<User> getAllFriendsOfUser(int userId) {
        String sql = "select u.* from FRIENDS f,USERS u WHERE u.USER_ID=f.FRIEND_ID AND f.USER_ID = ?";
        return jdbcTemplate.query(sql, UserDbStorage::makeUser, userId);
    }

    public List<User> getListOfCommonFriends(int id, int otherId) {
        String sql = "select u.* from USERS u, FRIENDS f, FRIENDS fr WHERE u.USER_ID = f.FRIEND_ID AND u.USER_ID = fr.FRIEND_ID AND f.USER_ID =?" +
                "           AND fr.USER_ID =?";
        return jdbcTemplate.query(sql, UserDbStorage::makeUser, id, otherId);
    }
}
