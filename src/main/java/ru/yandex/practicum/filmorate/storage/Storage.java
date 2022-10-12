package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.AlreadyExistException;
import ru.yandex.practicum.filmorate.exception.DoesntExistException;

import java.util.List;

public interface Storage<T> {

    void create(T data) throws AlreadyExistException;

    void update(T data) throws DoesntExistException;

    void delete(T data);

    List<T> getAll();

    T get(int id);
}
