package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private Map<Integer, User> users = new HashMap<>();
    private final static Logger log = LoggerFactory.getLogger(UserController.class);






    @GetMapping
    public List<User> getAllUsers(){
        List<User> listOfUsers = new ArrayList<>();
        for(User user: users.values()){
            listOfUsers.add(user);
        }
        log.debug("The current amount of Users: ", listOfUsers.size());
        return listOfUsers;
    }


    @PostMapping
    public User createUser(@RequestBody User user) throws UserValidationException{

        user.setId(user.getId());
        checkUserValidation(user);
        log.debug("POST /users ", user.getId());
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) throws UserValidationException{

        log.debug("PUT /users ", user.getId());
        if(users.containsKey(user.getId())){
            checkUserValidation(user);
            users.put(user.getId(), user);
        } else {
            throw  new UserValidationException("User doesn't exist");
        }
        return user;
    }

    public void checkUserValidation(User user) throws UserValidationException{
        if(user.getEmail() == null && user.getEmail().isEmpty() || !user.getEmail().contains("@")){
            throw new UserValidationException("Email is empty or incorrect.");
        }
        if(user.getLogin().isEmpty() || user.getLogin().contains(" ")){
            throw new UserValidationException("Login is incorrect.");
        }
        if(user.getBirthday().isAfter(LocalDate.now())){
            throw new UserValidationException("Birthday is incorrect.");
        }
        if(user.getName() == null){
            log.info("Name is empty.");
            user.setName(user.getLogin());
        }
    }

}
