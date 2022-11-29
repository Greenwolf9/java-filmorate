package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.dao.FilmDbStorage;
import ru.yandex.practicum.filmorate.dao.GenreImpl;
import ru.yandex.practicum.filmorate.dao.RatingMpaImpl;
import ru.yandex.practicum.filmorate.dao.UserDbStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {
	private final UserDbStorage userStorage;
	private final FilmDbStorage filmStorage;
	private final RatingMpaImpl mpa;
	private final GenreImpl genre;

	@Test
	void contextLoads() {
	}

	@Test
	public void testFindUserById() {
		Optional<User> userOptional = Optional.ofNullable(userStorage.get(1));
		assertThat(userOptional)
				.isPresent()
				.hasValueSatisfying(user ->
						assertThat(user).hasFieldOrPropertyWithValue("id", 1)
				);
	}

	@Test
	public void testFindFilmById() {
		Optional<Film> filmOptional = Optional.ofNullable(filmStorage.get(1));
		assertThat(filmOptional)
				.isPresent()
				.hasValueSatisfying(film ->
						assertThat(film).hasFieldOrPropertyWithValue("id", 1)
				);
	}

	@Test
	public void testFindMpaById() {
		Optional<Mpa> mpaOptional = Optional.ofNullable(mpa.get(1));
		assertThat(mpaOptional)
				.isPresent()
				.hasValueSatisfying(mpa ->
						assertThat(mpa).hasFieldOrPropertyWithValue("name", "G")
				);
	}

	@Test
	public void testFindGenreById() {
		Optional<Genre> genreOptional = Optional.ofNullable(genre.get(1));
		assertThat(genreOptional)
				.isPresent()
				.hasValueSatisfying(genre ->
						assertThat(genre).hasFieldOrPropertyWithValue("name", "Комедия")
				);
	}

}
