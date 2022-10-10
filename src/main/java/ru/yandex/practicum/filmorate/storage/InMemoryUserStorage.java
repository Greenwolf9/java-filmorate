package ru.yandex.practicum.filmorate.storage;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.AlreadyExistException;
import ru.yandex.practicum.filmorate.exception.DoesntExistException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage{
    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public void create(User user) throws AlreadyExistException {
        if(users.containsKey(user.getId())){
            throw new AlreadyExistException("User already exists.");
        }
        users.put(user.getId(), user);
    }

    @Override
    public void delete(User user) {
        users.remove(user.getId());
    }

    @Override
    public void update(User user) throws DoesntExistException{
        if (!users.containsKey(user.getId())) {
            throw new DoesntExistException("User doesn't exist.");
        }
        users.put(user.getId(), user);
    }

    @Override
    public List<User> getAll(){
        return new ArrayList<>(users.values());
    }

    @SneakyThrows
    @Override
    public User get(int id) {
        if (!users.containsKey(id)) {
            throw new DoesntExistException("User doesn't exist.");
        }return users.get(id);
    }
}
