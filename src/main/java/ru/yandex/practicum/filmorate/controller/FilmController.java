package ru.yandex.practicum.filmorate.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {

    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private Map<Integer,Film> films = new HashMap<>();



    @GetMapping
    public List<Film> getAllFilms(){
        List<Film> listOfFilms = new ArrayList<>();
        for (Film film: films.values()) {
            listOfFilms.add(film);
        }
        log.debug("The current amount of Films: ", listOfFilms.size());
        return listOfFilms;
    }

    @PostMapping
    public Film create(@RequestBody Film film) throws Exception{

        film.setId(film.getId());
        checkValidation(film);
        films.put(film.getId(), film);
        log.debug("POST /users: ", film.getId());
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) throws Exception{
        log.info("PUT /films", film.getId());
        if(films.containsKey(film.getId())){
            checkValidation(film);
            films.put(film.getId(), film);
        } else {
            throw new FilmValidationException("Film doesn't exist.");
        }

        return film;
    }

    public void checkValidation(Film film) throws Exception{
        if(film.getName().isEmpty()){
            throw new FilmValidationException("Film has no name.");
        } else if (film.getDescription().length() > 200){
            throw new FilmValidationException("Description is too long.");
        } else if(film.getDuration()<0){
            throw new FilmValidationException("Duration has to be more than 0");
        } else if(film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))){
            throw new FilmValidationException("The film has to be released after 28/12/1895");
        }
    }

}
