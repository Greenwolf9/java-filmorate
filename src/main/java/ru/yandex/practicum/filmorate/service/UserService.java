package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.AlreadyExistException;
import ru.yandex.practicum.filmorate.exception.DoesntExistException;
import ru.yandex.practicum.filmorate.exception.UserValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    InMemoryUserStorage inMemoryUserStorage;
    private int userId = 0;

    @Autowired
    public UserService(InMemoryUserStorage inMemoryUserStorage){
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public List<User> getAllUsers(){
        return inMemoryUserStorage.getAll();
    }

    public User getUser(int id) {
        return inMemoryUserStorage.get(id);
        }
    public Set<User> getListOfCommonFriends(int id, int otherId){
        User user = getUser(id);
        Set<User> friendsOfUser = new HashSet<>();
        for (Integer friendsId: user.getFriendsIds()){
            if(friendsId != otherId){
            friendsOfUser.add(getUser(friendsId));
            }
        }
        User otherUser = getUser(otherId);
        Set<User> friendsOfOtherUser = new HashSet<>();
        for (Integer friendsId: otherUser.getFriendsIds()){
            if(friendsId != id) {
                friendsOfOtherUser.add(getUser(friendsId));
            }
        }
        Set<User> united = new HashSet<>();
        united.addAll(friendsOfUser);
        united.addAll(friendsOfOtherUser);
        return united;
    }

    public User createUser(User user) throws UserValidationException, AlreadyExistException {
        checkUserValidation(user);
        user.setId(++userId);
        inMemoryUserStorage.create(user);
        return user;
    }

    public User updateUser(User user) throws UserValidationException, DoesntExistException{
        checkUserValidation(user);
        inMemoryUserStorage.update(user);
        return user;
    }

    public void delete(User user) {
        inMemoryUserStorage.delete(user);
    }

    public void addToFriend(int userId, int friendsId){
        final User user = getUser(userId);
        final User friend = getUser(friendsId);
        if(friendsId > 0 && getAllUsers().contains(friend)) {
        user.addToFriend(friendsId);
        }
    }
    public void deleteFromFriend(int userId, int friendsId){
        User user = getUser(userId);
        user.deleteFromFriend(friendsId);
    }

    public Set<User> getAllFriendsOfUser(int userId){
        User user = getUser(userId);
        Set<User> friends = new HashSet<>();
        for (Integer friendsId: user.getFriendsIds()){
            friends.add(getUser(friendsId));
            addToFriend(friendsId, userId);
        }
        return friends;
    }

    private void checkUserValidation(User user) throws UserValidationException{
        if(user.getEmail() == null && user.getEmail().isEmpty() || !user.getEmail().contains("@")){
            throw new UserValidationException("Email is empty or incorrect.");
        }
        if(user.getLogin().isEmpty() || user.getLogin().contains(" ")){
            throw new UserValidationException("Login is incorrect.");
        }
        if(user.getBirthday().isAfter(LocalDate.now())){
            throw new UserValidationException("Birthday is incorrect.");
        }
        if(user.getName().isEmpty()){
            user.setName(user.getLogin());
        }
    }
}
