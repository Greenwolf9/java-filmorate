package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.dao.FriendsImpl;
import ru.yandex.practicum.filmorate.dao.UserDbStorage;
import ru.yandex.practicum.filmorate.exception.UserValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();
    private UserStorage userStorage = new UserDbStorage(jdbcTemplate);
    private FriendsImpl friends = new FriendsImpl(jdbcTemplate);
    private UserService userService = new UserService(userStorage, friends);
    private UserController userController = new UserController(userService);

    @Test
    void createUser() {
        User user = new User("", "RabbitInTheHole", "John McCay",
                LocalDate.of(2002, 02, 03));

        UserValidationException thrown = assertThrows(UserValidationException.class, () -> {
            userController.createUser(user);
        });

        assertEquals("Email is empty or incorrect.", thrown.getMessage());

        User user2 = new User("mail@yandex.ru", "", "John McCay",
                LocalDate.of(2002, 02, 03));

        UserValidationException thrown2 = assertThrows(UserValidationException.class, () -> {
            userController.createUser(user2);
        });

        assertEquals("Login is incorrect.", thrown2.getMessage());

        User user3 = new User("mail@yandex.ru", "johnIII", "John McCay",
                LocalDate.of(2050, 02, 03));

        UserValidationException thrown3 = assertThrows(UserValidationException.class, () -> {
            userController.createUser(user3);
        });

        assertEquals("Birthday is incorrect.", thrown3.getMessage());

    }
}