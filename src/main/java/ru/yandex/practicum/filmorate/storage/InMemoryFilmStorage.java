package ru.yandex.practicum.filmorate.storage;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.AlreadyExistException;
import ru.yandex.practicum.filmorate.exception.DoesntExistException;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage{

    private final Map<Integer, Film> films = new HashMap<>();

    @Override
    public void create(Film film) throws AlreadyExistException{
        if(films.containsKey(film.getId())){
            throw new AlreadyExistException("Film already exists.");
        }
        films.put(film.getId(), film);
    }

    @Override
    public void delete(Film film) {
        films.remove(film.getId());
    }

    @Override
    public void update(Film film) throws DoesntExistException{
        if(!films.containsKey(film.getId())){
            throw new DoesntExistException("Film doesn't exist.");
        }
        films.put(film.getId(), film);
    }

    @Override
    public List<Film> getAll(){
        return new ArrayList<>(films.values());
    }

    @SneakyThrows
    @Override
    public Film get(int id)  {
        if(!films.containsKey(id)){
            throw new DoesntExistException("Film doesn't exist.");
    }
        return films.get(id);
    }

    public List<Film> getPopularFilm(int count){
       return films.values().stream()
               .sorted(Comparator.comparing(Film::getRate))
               .limit(count)
               .collect(Collectors.toList());
    }
}
