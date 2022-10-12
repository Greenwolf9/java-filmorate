package ru.yandex.practicum.filmorate.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.AlreadyExistException;
import ru.yandex.practicum.filmorate.exception.DoesntExistException;
import ru.yandex.practicum.filmorate.exception.FilmValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.time.LocalDate;
import java.util.List;

@Service
public class FilmService {

    private InMemoryFilmStorage inMemoryFilmStorage;
    private int filmId = 0;

    @Autowired
    public FilmService(InMemoryFilmStorage inMemoryFilmStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }

    public List<Film> getAllFilms() {
        return inMemoryFilmStorage.getAll();
    }

    public Film getFilm(int id) {
        return inMemoryFilmStorage.get(id);
    }

    public Film createFilm(Film film) throws FilmValidationException {
        checkValidation(film);
        film.setId(++filmId);
        try {
            inMemoryFilmStorage.create(film);
        } catch (AlreadyExistException exception) {
            System.out.println(exception.getMessage());
        }
        return film;
    }

    public Film updateFilm(Film film) throws FilmValidationException, DoesntExistException {
        checkValidation(film);
        inMemoryFilmStorage.update(film);
        return film;
    }

    public void delete(Film film) {
        inMemoryFilmStorage.delete(film);
    }

    public void addLike(int userId, int filmId) {
        final Film film = getFilm(filmId);
        film.addLike(userId);
    }

    @SneakyThrows
    public void removeLike(int userId, int filmId) {
        final Film film = getFilm(filmId);
        film.removeLike(userId);
    }

    public List<Film> getPopularFilms(int count) {
        return inMemoryFilmStorage.getPopularFilm(count);
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
