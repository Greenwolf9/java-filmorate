package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.AlreadyExistException;
import ru.yandex.practicum.filmorate.exception.DoesntExistException;
import ru.yandex.practicum.filmorate.exception.FilmValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@RequestMapping
public class FilmController {

    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/films")
    public List<Film> getAllFilms() {
        log.debug("The current amount of Films: " + filmService.getAllFilms().size());
        return filmService.getAllFilms();
    }

    @GetMapping("/films/{id}")
    public Film getFilm(@PathVariable Integer id) {
        log.info("GET / films / id " + id);
        return filmService.getFilm(id);
    }

    @GetMapping("/mpa")
    public List<Mpa> getAllMpa() {
        log.info("GET / mpa ");
        return filmService.getAllMpa();
    }

    @GetMapping("/mpa/{id}")
    public Mpa getMpa(@PathVariable Integer id) {
        log.info("GET / mpa / id " + id);
        return filmService.getMpa(id);
    }

    @GetMapping("/genres")
    public List<Genre> getAllGenres() {
        log.info("GET / genre ");
        return filmService.getAllGenres();
    }

    @GetMapping("/genres/{id}")
    public Genre getGenre(@PathVariable Integer id) {
        log.info("GET / genre / id " + id);
        return filmService.getGenre(id);
    }

    @PostMapping("/films")
    public Film createFilm(@RequestBody Film film) throws FilmValidationException, AlreadyExistException {
        log.debug("POST /films: " + film.getId());
        return filmService.createFilm(film);
    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film) throws FilmValidationException, DoesntExistException {
        log.info("PUT /films" + film.getId());
        return filmService.updateFilm(film);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        log.info("PUT films/{id} " + id + "/like/{userId} " + userId);
        filmService.addLike(userId, id);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void removeLike(@PathVariable int id, @PathVariable int userId) {
        filmService.removeLike(userId, id);
    }

    @GetMapping("/films/popular")
    public List<Film> getMostPopularFilms(@RequestParam(value = "count", defaultValue = "10") Integer count) {
        return filmService.getMostPopularFilms(count);
    }
}
