package com.movies.service;

import com.movies.entity.Movie;
import com.movies.repository.MovieRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class MovieService {

//    @Inject
//    private MovieRepository movieRepository;

    @Inject
    private SomeExternalService externalService;

    public List<Movie> getAllMovies() {
        return  externalService.getAllMovies();
    }

    public Movie addMovie(Movie movie) {
        System.out.println("Called addMovie method in MovieService");
        return Movie.builder().build();
    }

    public Movie updateMovie(Long id, Movie movie) {
        System.out.println("Called updateMovie method in MovieService");
        return Movie.builder().build();
    }

    public boolean deleteMovie(Long id) {
        System.out.println("Called deleteMovie method in MovieService");
        return true;
    }
}
