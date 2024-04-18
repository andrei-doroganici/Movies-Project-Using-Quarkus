package com.movies.service;

import com.movies.entity.Movie;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Comparator;
import java.util.List;

@ApplicationScoped
public class SomeExternalService {

    @Inject
    MovieService movieService;

    public Movie getMovieById(Long id) {
        return movieService.getAllMovies().stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Movie getMovieByName(String name) {
        return movieService.getAllMovies().stream()
                .filter(m -> m.getTitle().equals(name))
                .findFirst()
                .orElse(null);
    }

    public List<Movie> getAllMoviesSortedAscByYear() {
        return movieService.getAllMovies().stream()
                .sorted(Comparator.comparing(m -> m.getReleaseYear()))
                .toList();
    }
}
