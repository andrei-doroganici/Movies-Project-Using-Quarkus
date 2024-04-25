package com.movies.service;

import com.movies.entity.Movie;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@QuarkusTest
@ExtendWith(MockitoExtension.class)
public class SomeExternalServiceMockitoTest {

    @InjectMock
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
    }

    // serviciul extern intoarece acelasi raspuns pentru orice chemare
    @Test
    public void getAllMovies_AlwaysReturnsSameList() {
        when(someExternalService.getAllMovies()).thenReturn(movieList);

        assertEquals(movieList, someExternalService.getAllMovies());
        assertEquals(movieList, someExternalService.getAllMovies());
        assertFalse(movieList.isEmpty());
        assertEquals(3, movieList.size());
    }

    // serviciul extern intoarce raspunsuri diferite pentru chemari consecutive
    @Test
    public void testGetMoviesSortedReturnsDifferentListsConsecutively() {
        List<Movie> firstCallMovies = List.of(movie1);
        List<Movie> secondCallMovies = List.of(movie2);

        when(someExternalService.getAllMovies()).thenReturn(firstCallMovies, secondCallMovies);

        assertNotNull(firstCallMovies);
        assertNotNull(secondCallMovies);
        assertEquals(firstCallMovies, someExternalService.getAllMovies());
        assertEquals(secondCallMovies, someExternalService.getAllMovies());
    }

    // serviciul extern intoarce raspusnuri in functie de valoarea la parametri
    @Test
    public void testGetMoviesByGenreBasedOnParameters() {
        when(someExternalService.getAllMovies()).thenReturn(movieList);
        when(someExternalService.getMoviesByGenre("Fiction")).thenReturn(List.of(movie1));
        when(someExternalService.getMoviesByGenre("Drama")).thenReturn(List.of(movie2, movie3));

        List<Movie> fictionMovies = someExternalService.getMoviesByGenre("Fiction");
        List<Movie> dramaMovies = someExternalService.getMoviesByGenre("Drama");

        assertEquals(1, fictionMovies.size());
        assertEquals(movie1, fictionMovies.get(0));

        assertEquals(2, dramaMovies.size());
        assertEquals(movie2, dramaMovies.get(0));
    }

    // serviciul extern semnaleaza o careva eroare
    @Test
    public void getAllMovies_doesThrowsException() {
        when(someExternalService.getAllMovies()).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> someExternalService.getAllMovies());
    }

}
