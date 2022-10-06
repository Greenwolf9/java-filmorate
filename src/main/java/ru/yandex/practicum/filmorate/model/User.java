package ru.yandex.practicum.filmorate.model;
import lombok.*;

import java.time.LocalDate;


@Data
@NoArgsConstructor
public class User {

    private int id = 1;
    private String email;
    private  String login;
    private String name;
    private LocalDate birthday;


public User(final String email, final String login, String name, final LocalDate birthday){
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
