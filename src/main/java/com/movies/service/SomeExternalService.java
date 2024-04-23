package com.movies.service;

import com.movies.entity.Movie;
import com.movies.repository.MovieRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class SomeExternalService {

    @Inject
    MovieRepository movieRepository;

    public List<Movie> getAllMovies() {
        return movieRepository.getMovies();
    }

    public Movie getMovieById(Long id) {
        return movieRepository.getMovies().stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Movie> getMoviesByGenre(String genre) {
        return movieRepository.getMovies().stream()
                .filter(m -> m.getGenre().equalsIgnoreCase(genre))
                .collect(Collectors.toList());
    }

    public List<Movie> getMoviesByDirector(String director) {
        return movieRepository.getMovies().stream()
                .filter(m -> m.getDirector().equalsIgnoreCase(director))
                .collect(Collectors.toList());
    }
}
