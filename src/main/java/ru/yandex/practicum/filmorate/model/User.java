package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private int id;

    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
    private Set<Integer> friendsIds = new HashSet<>();



    public User(int id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    public User(String email, String login, String name, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    public void addToFriend(int friendsId) {
        friendsIds.add(friendsId);
    }

    public void deleteFromFriend(int friendsId) {
        friendsIds.remove(friendsId);
    }

}
