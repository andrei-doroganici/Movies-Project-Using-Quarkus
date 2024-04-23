package com.movies.service;

import com.movies.entity.Movie;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@ExtendWith(MockitoExtension.class)
public class SomeExternalServiceTestMockitoSpy {

    @InjectSpy
    SomeExternalService someExternalService;

    private Movie movie1;

    private Movie movie2;

    private Movie movie3;

    List<Movie> movieList;

    @BeforeEach
    void setUp(){
        movie1 = Movie.builder()
                .id(1L)
                .title("testMovie1")
                .director("John Doe")
                .releaseYear(2010)
                .genre("Fiction")
                .build();

        movie2 = Movie.builder()
                .id(2L)
                .title("testMovie2")
                .director("John Doe")
                .releaseYear(1994)
                .genre("Drama")
                .durationMinutes(222)
                .rating(9.3f)
                .plotSummary("Lorem ipsum dolor sit amet")
                .language("English")
                .country("United States")
                .build();

        movie3 = Movie.builder()
                .id(3L)
                .title("testMovie3")
                .director("Quentin Tarantino")
                .releaseYear(2024)
                .genre("Drama")
                .durationMinutes(121)
                .rating(9.3f)
                .plotSummary("Lorem ipsum dolor sit amet")
                .language("English")
                .country("China")
                .build();

        movieList = List.of(movie1, movie2, movie3);
        Mockito.doReturn(movieList).when(someExternalService).getAllMovies();
    }

    @Test
    public void testSpyServiceModifiesBehaviorSelectively() {
        // Since someExternalService is already a spy due to @InjectSpy, you can directly modify its behavior
        List<Movie> expectedMovies = movieList;

        Mockito.doReturn(expectedMovies).when(someExternalService).getMoviesByGenre("Drama");

        assertEquals(expectedMovies, someExternalService.getMoviesByGenre("Drama"));
        Mockito.verify(someExternalService, Mockito.times(1)).getMoviesByGenre("Drama");
        Mockito.doReturn(List.of()).when(someExternalService).getMoviesByGenre("Drama");
        assertEquals(List.of(), someExternalService.getMoviesByGenre("Drama"));
    }

    @Test
    public void testGetMoviesByGenreWithDynamicResponse() {
        // Prepare test data
        List<Movie> allMovies = List.of(
                new Movie(1L, "Drama Movie", "Director A", 2020, "Drama"),
                new Movie(2L, "Action Movie", "Director B", 2021, "Action")
        );

        // Setup the spy to return all movies
        Mockito.doReturn(allMovies).when(someExternalService).getAllMovies();

        // Use thenAnswer to dynamically filter movies by genre
        Mockito.when(someExternalService.getMoviesByGenre(Mockito.anyString())).thenAnswer(invocation -> {
            String genre = invocation.getArgument(0, String.class);
            return allMovies.stream()
                    .filter(movie -> movie.getGenre().equals(genre))
                    .collect(Collectors.toList());
        });

        // Execute and verify
        List<Movie> dramaMovies = someExternalService.getMoviesByGenre("Drama");
        assertEquals(1, dramaMovies.size());
        assertEquals("Drama Movie", dramaMovies.get(0).getTitle());

        // Verify that the method was called with the correct argument
        Mockito.verify(someExternalService).getMoviesByGenre("Drama");
    }

}
