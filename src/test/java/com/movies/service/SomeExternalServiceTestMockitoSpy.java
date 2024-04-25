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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
//        Mockito.doReturn(movieList).when(someExternalService).getAllMovies();
    }

    // serviciul extern intoarce lista de valori definite in clasa SomeExternalService
    @Test
    void given_getAllMovies_shouldReturnAllMovies() {
        List<Movie> allMovies = someExternalService.getAllMovies();

        assertNotNull(allMovies);
        assertEquals(3, allMovies.size());
        assertNotNull(allMovies.get(0));
        assertNotEquals(movieList, allMovies);
        Mockito.verify(someExternalService).getAllMovies();
    }
}
