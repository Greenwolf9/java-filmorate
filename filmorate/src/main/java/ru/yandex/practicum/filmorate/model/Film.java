package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class Film {
    private int id = 1;
    private final String name;
    private final String description;
    private final LocalDate releaseDate;
    private final int duration;



    public Film(final String name, final String description, final LocalDate releaseDate, final int duration){
        this.id = generateId();
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public int generateId(){
        return id++;
    }

}
