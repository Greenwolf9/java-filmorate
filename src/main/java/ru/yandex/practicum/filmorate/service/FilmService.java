package ru.yandex.practicum.filmorate.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.GenreDbStorage;
import ru.yandex.practicum.filmorate.dao.LikeDbStorage;
import ru.yandex.practicum.filmorate.dao.MpaDbStorage;
import ru.yandex.practicum.filmorate.exception.AlreadyExistException;
import ru.yandex.practicum.filmorate.exception.DoesntExistException;
import ru.yandex.practicum.filmorate.exception.FilmValidationException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private FilmStorage filmStorage;
    private final MpaDbStorage mpaDbStorage;
    private final GenreDbStorage genreDbStorage;
    private final LikeDbStorage likes;


    @Autowired
    public FilmService(FilmStorage filmStorage, MpaDbStorage mpaDbStorage, GenreDbStorage genreDbStorage, LikeDbStorage likes) {
        this.filmStorage = filmStorage;
        this.mpaDbStorage = mpaDbStorage;
        this.genreDbStorage = genreDbStorage;
        this.likes = likes;

    }

    public List<Film> getAllFilms() {
        List<Film> listOfFilms = filmStorage.getAll();
        Map<Integer, Film> map = listOfFilms.stream()
                .collect(Collectors.toMap(Film::getId, film -> film));
        List<Film> films = new ArrayList<>();
        for (Integer i : map.keySet()) {
            final Set<Genre> genres = genreDbStorage.getGenreByFilmId(i);
            final Film film = filmStorage.get(i);
            film.setGenres(genres);
            films.add(film);
        }
        return films;
    }

    @SneakyThrows
    public Film getFilm(int id) {
        if (!filmStorage.getAll().stream().map(Film::getId).anyMatch(f -> f == id)) {
            throw new DoesntExistException("Film id " + id + " doesn't exist");
        }
        final Set<Genre> genres = genreDbStorage.getGenreByFilmId(id);
        final Film film = filmStorage.get(id);
        if (genres.size() > 0) {
            film.setGenres(genres);
        }
        return film;
    }

    public Film createFilm(Film film) throws FilmValidationException, AlreadyExistException {
        checkValidation(film);
        if (filmStorage.getAll().stream().map(Film::getId).anyMatch(f -> f == film.getId())) {
            throw new AlreadyExistException("Film with this id {} already exists" + film.getId());
        }
        filmStorage.create(film);
        final Set<Genre> genres = genreDbStorage.getGenreByFilmId(film.getId());
        if (genres.size() > 0) {
            film.setGenres(genres);
        }
        return film;
    }

    public Film updateFilm(Film film) throws FilmValidationException, DoesntExistException {
        checkValidation(film);
        if (!filmStorage.getAll().stream().map(Film::getId).anyMatch(f -> f == film.getId())) {
            throw new DoesntExistException("Film with this id {} doesn't exist" + film.getId());
        }
        filmStorage.update(film);
        final Set<Genre> genres = genreDbStorage.getGenreByFilmId(film.getId());
        if (genres.size() > 0) {
            film.setGenres(genres);
        }
        return film;
    }

    public void delete(Film film) {
        filmStorage.delete(film);
    }

    public void addLike(int userId, int filmId) {
        likes.addLike(userId, filmId);
    }

    @SneakyThrows
    public void removeLike(int userId, int filmId) {
        if (userId < 0) {
            throw new NotFoundException("Not found. Wrong userId");
        }
        likes.removeLike(userId, filmId);
    }

    public List<Film> getMostPopularFilms(int count) {
        return likes.getMostPopularFilms(count);
    }

    @SneakyThrows
    public Mpa getMpa(int id) {
        if (!mpaDbStorage.getAll().stream().map(Mpa::getId).anyMatch(m -> m == id)) {
            throw new DoesntExistException("MPA with id " + id + " doesn't exist!");
        }
        return mpaDbStorage.get(id);
    }

    public List<Mpa> getAllMpa() {
        return mpaDbStorage.getAll();
    }

    @SneakyThrows
    public Genre getGenre(int id) {
        if (!genreDbStorage.getAll().stream().map(Genre::getId).anyMatch(m -> m == id)) {
            throw new DoesntExistException("Genre with id " + id + "doesn't exist");
        }
        return genreDbStorage.get(id);
    }

    public List<Genre> getAllGenres() {
        return genreDbStorage.getAll();
    }

    private void checkValidation(Film film) throws FilmValidationException {
        if (film.getName().isEmpty()) {
            throw new FilmValidationException("Film has no name.");
        } else if (film.getDescription().length() > 200) {
            throw new FilmValidationException("Description is too long.");
        } else if (film.getDuration() < 0) {
            throw new FilmValidationException("Duration has to be more than 0");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new FilmValidationException("The film has to be released after 28/12/1895");
        }
    }
}
