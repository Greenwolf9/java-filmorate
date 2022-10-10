package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.exception.DoesntExistException;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Film {
    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private Set<Integer> userIds = new HashSet<>();
    private int rate;

    public Film(String name, String description, LocalDate releaseDate, int duration){
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public void addLike(int id){
        userIds.add(id);
        rate = userIds.size();
    }

    public void removeLike (int id) throws DoesntExistException{
        if(!userIds.contains(id)) {
            throw new DoesntExistException("No Like");
        }
        userIds.remove(id);
        rate = userIds.size();
    }
}
