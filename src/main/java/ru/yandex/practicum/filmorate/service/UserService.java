package ru.yandex.practicum.filmorate.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FriendDbStorage;
import ru.yandex.practicum.filmorate.exception.AlreadyExistException;
import ru.yandex.practicum.filmorate.exception.DoesntExistException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.UserValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {
    private UserStorage userStorage;
    private FriendDbStorage friends;


    private int userId = 0;

    @Autowired
    public UserService(UserStorage userStorage, FriendDbStorage friends) {
        this.userStorage = userStorage;
        this.friends = friends;
    }

    public List<User> getAllUsers() {
        return userStorage.getAll();
    }

    @SneakyThrows
    public User getUser(int id) {
        if (!userStorage.getAll().stream().map(User::getId).anyMatch(a -> a == id)) {
            throw new DoesntExistException("User id " + id + " doesn't exist");
        }
        return userStorage.get(id);
    }

    public List<User> getListOfCommonFriends(int id, int otherId) {
        return friends.getListOfCommonFriends(id, otherId);
    }

    public User createUser(User user) throws UserValidationException, AlreadyExistException {
        checkUserValidation(user);
        if (userStorage.getAll().stream().map(User::getEmail).anyMatch(m -> m.equals(user.getEmail()))) {
            throw new AlreadyExistException("User with this email {} already exists" + user.getEmail());
        }
        userStorage.create(user);
        return user;
    }

    public User updateUser(User user) throws UserValidationException, DoesntExistException {
        checkUserValidation(user);
        if (!userStorage.getAll().stream().map(User::getId).anyMatch(m -> m == user.getId())) {
            throw new DoesntExistException("User with id {} doesn't exist" + user.getId());
        }
        userStorage.update(user);
        return user;
    }

    public void delete(User user) {
        userStorage.delete(user);
    }

    @SneakyThrows
    public void addToFriend(int userId, int friendsId) {
        if (userStorage.getAll().stream().map(User::getId).anyMatch(m -> m == friendsId)
                && friendsId > 0) {
            friends.addToFriend(userId, friendsId);
            final User user = getUser(userId);
            user.addToFriend(friendsId);
        } else {
            throw new NotFoundException("Friend with id " + friendsId + "wasn't found");
        }
    }

    public void deleteFromFriend(int userId, int friendsId) {
        friends.deleteFromFriend(userId, friendsId);
        final User user = getUser(userId);
        user.deleteFromFriend(friendsId);
    }

    public List<User> getAllFriendsOfUser(int userId) {
        return friends.getAllFriendsOfUser(userId);
    }

    private void checkUserValidation(User user) throws UserValidationException {
        if (user.getEmail() == null && user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            throw new UserValidationException("Email is empty or incorrect.");
        }
        if (user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            throw new UserValidationException("Login is incorrect.");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new UserValidationException("Birthday is incorrect.");
        }
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
    }
}
