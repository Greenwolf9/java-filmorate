//package ru.yandex.practicum.filmorate.service;
//
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.mockito.Mock;
//import org.springframework.test.context.event.annotation.BeforeTestClass;
//import ru.yandex.practicum.filmorate.dao.FilmDbStorage;
//import ru.yandex.practicum.filmorate.dao.GenreImpl;
//import ru.yandex.practicum.filmorate.dao.LikesImpl;
//import ru.yandex.practicum.filmorate.dao.RatingMpaImpl;
//import ru.yandex.practicum.filmorate.exception.FilmValidationException;
//import ru.yandex.practicum.filmorate.model.Film;
//import ru.yandex.practicum.filmorate.model.Genre;
//import ru.yandex.practicum.filmorate.model.RatingMPA;
//import ru.yandex.practicum.filmorate.storage.Storage;
//
//import java.time.LocalDate;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.*;
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//class FilmServiceTest {
//
//    private
//    FilmService filmService;
//    @Mock
//    private FilmDbStorage storage;
//    @Mock
//    private RatingMpaImpl ratingMpaImpl;
//    @Mock
//    private GenreImpl genreImpl;
//    @Mock
//    private LikesImpl likes;
//    private static Film testFilm;
//
//    @BeforeAll
//    public static void prepareTestDate(){
//        testFilm = Film.builder().id(1).name("Kill Bill").description("some description")
//                .releaseDate(LocalDate.of(2000,01,02)).duration(100).rate(1)
//                .rating(new RatingMPA(1, "G"))
//                .build();
//
//    }
//    @BeforeAll
//    public void setUp() throws FilmValidationException {
//        filmService = new FilmService(storage, ratingMpaImpl,genreImpl,likes);
//        filmService.createFilm(testFilm);
//    }
//    @Test
//    void getAllFilms() {
//        List<Film> filmList = filmService.getAllFilms();
//        assertEquals(filmList.size(),1);
//    }
//
//    @Test
//    void getFilm() {
//    }
//
//    @Test
//    void createFilm() {
//    }
//
//    @Test
//    void updateFilm() {
//    }
//
//    @Test
//    void delete() {
//    }
//
//    @Test
//    void addLike() {
//    }
//
//    @Test
//    void removeLike() {
//    }
//
//    @Test
//    void getPopularFilms() {
//    }
//
//    @Test
//    void getMpa() {
//    }
//
//    @Test
//    void getAllMpa() {
//    }
//
//    @Test
//    void getGenre() {
//    }
//
//    @Test
//    void getAllGenres() {
//    }
//}