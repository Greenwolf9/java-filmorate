package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Film {
    private int id = 1;
    private  String name;
    private  String description;
    private  LocalDate releaseDate;
    private  int duration;



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
