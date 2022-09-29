package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.FilmValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    FilmController filmController = new FilmController();

    @Test
    void createFilm() {
        Film film = new Film("", "about some staff",
                LocalDate.of(2022, 9,29), 45);

        FilmValidationException thrown = assertThrows(FilmValidationException.class, () ->{
            filmController.create(film);
        });
        assertEquals("Film has no name.", thrown.getMessage());

        Film film2 = new Film("hello world", "about some staffabout some staffabout some staffabout " +
                "some staffabout some staffabout some staff" +
                "        \"about some staffabout some staffabout some staffabout some staff" +
                "about some staffabout some staffabout some staffabout some staff" +
                "        \"about some staffabout some staffabout some staffabout some \n",
                LocalDate.of(2022, 9,29), 45);

        FilmValidationException thrown2 = assertThrows(FilmValidationException.class, () ->{
            filmController.create(film2);
        });
        assertEquals("Description is too long.", thrown2.getMessage());

        Film film3 = new Film("hello world", "about some staff",
                LocalDate.of(2022, 9,29), -45);

        FilmValidationException thrown3 = assertThrows(FilmValidationException.class, () ->{
            filmController.create(film3);
        });
        assertEquals("Duration has to be more than 0", thrown3.getMessage());

        Film film4 = new Film("hello world", "about some staff",
                LocalDate.of(1800, 9,29), 45);

        FilmValidationException thrown4 = assertThrows(FilmValidationException.class, () ->{
            filmController.create(film4);
        });
        assertEquals("The film has to be released after 28/12/1895", thrown4.getMessage());
    }
}