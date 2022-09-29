package ru.yandex.practicum.filmorate.model;
import lombok.*;

import java.time.LocalDate;


@Data

public class User {

    private int id = 1;
    private final String email;
    private final String login;
    private String name;
    private final LocalDate birthday;

public User(String email, String login, String name, LocalDate birthday){
    this.id = generateId();
    this.email = email;
    this.login = login;
    this.name = name;
    this.birthday = birthday;
}

    public int generateId(){
        return id++;
    }


}
