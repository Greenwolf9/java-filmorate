package ru.yandex.practicum.filmorate.exception;

public class DoesntExistException extends Exception{
    public DoesntExistException(String message){
        super(message);
    }
}
