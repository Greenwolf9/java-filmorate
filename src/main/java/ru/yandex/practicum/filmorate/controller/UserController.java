package ru.yandex.practicum.filmorate.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.AlreadyExistException;
import ru.yandex.practicum.filmorate.exception.DoesntExistException;
import ru.yandex.practicum.filmorate.exception.UserValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);
    UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers(){
        log.info("GET / users // The current amount of Users: " + userService.getAllUsers().size());
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Integer id) {
        log.info("GET / users / id " + id);
        return userService.getUser(id);
    }

    @PostMapping
    public User createUser(@RequestBody User user) throws UserValidationException, AlreadyExistException {
        log.debug("POST /users " + user.getId());
        return userService.createUser(user);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) throws UserValidationException, DoesntExistException {
        log.debug("PUT /users " + user.getId());
        userService.updateUser(user);
        return user;
    }

    @DeleteMapping
    public void deleteUser(@RequestBody User user){
        userService.delete(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addToFriend(@PathVariable int id, @PathVariable int friendId){
        log.info("PUT/{id} "+ id + "/friends/{friendId} " + friendId);
        userService.addToFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFromFriend(@PathVariable int id, @PathVariable int friendId){
        log.info("DELETE /{id} "+ id + "/friends/{friendId} " + friendId);
        userService.deleteFromFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Set<User> getListOfFriends(@PathVariable int id){
        log.info("GET /{id}/friends: " +userService.getAllFriendsOfUser(id).size());
        return userService.getAllFriendsOfUser(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Set<User> getListOfCommonFriends(@PathVariable int id, @PathVariable int otherId){
        return userService.getListOfCommonFriends(id, otherId);
    }
}
