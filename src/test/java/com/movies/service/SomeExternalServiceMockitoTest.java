package com.movies.service;

import com.movies.entity.Movie;
import com.movies.repository.MovieRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.Mock;
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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@ExtendWith(MockitoExtension.class)
public class SomeExternalServiceMockitoTest {

    @InjectMock
    SomeExternalService someExternalService;

//    @Inject
//    MovieRepository movieRepository;

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

    @Test
    public void testGetMovieByIdAlwaysReturnsSameMovie() {
        Mockito.when(someExternalService.getAllMovies()).thenReturn(movieList);
        Mockito.when(someExternalService.getMovieById(1L)).thenReturn(movie1);

        Movie expectedMovie = movie1;

        Movie resultMovie1 = someExternalService.getMovieById(1L);
        Movie resultMovie2 = someExternalService.getMovieById(1L);

        assertFalse(movieList.isEmpty());
        assertEquals(expectedMovie, resultMovie1);
        assertEquals(expectedMovie, resultMovie2);
    }

    @Test
    public void testGetMoviesSortedReturnsDifferentListsConsecutively() {
        List<Movie> firstCallMovies = List.of(movie1);
        List<Movie> secondCallMovies = List.of(movie2);

        Mockito.when(someExternalService.getAllMovies())
                .thenReturn(firstCallMovies, secondCallMovies);

        assertNotNull(firstCallMovies);
        assertNotNull(secondCallMovies);
        assertEquals(firstCallMovies, someExternalService.getAllMovies());
        assertEquals(secondCallMovies, someExternalService.getAllMovies());
    }

    @Test
    public void testGetMoviesByGenreBasedOnParameters() {
        Mockito.when(someExternalService.getAllMovies()).thenReturn(movieList);
        Mockito.when(someExternalService.getMoviesByGenre("Fiction")).thenReturn(List.of(movie1));
        Mockito.when(someExternalService.getMoviesByGenre("Drama")).thenReturn(List.of(movie2, movie3));


        List<Movie> fictionMovies = someExternalService.getMoviesByGenre("Fiction");
        List<Movie> dramaMovies = someExternalService.getMoviesByGenre("Drama");

        assertEquals(1, fictionMovies.size());
        assertEquals(movie1, fictionMovies.get(0));

        assertEquals(2, dramaMovies.size());
        assertEquals(movie2, dramaMovies.get(0));
    }

    @Test
    public void getAllMovies_doesThrowsException() {
        Mockito.when(someExternalService.getAllMovies()).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> someExternalService.getAllMovies());
    }

//    @Test
//    public void testSpyServiceModifiesBehaviorSelectively() {
//        SomeExternalService spyService = Mockito.spy(someExternalService);
//        List<Movie> expectedMovies = movieList;
//
//        Mockito.doReturn(expectedMovies).when(spyService).getMoviesByGenre("Drama");
//
//        assertEquals(expectedMovies, spyService.getMoviesByGenre("Drama"));
//        Mockito.verify(spyService, Mockito.times(1)).getMoviesByGenre("Drama");
//        Mockito.doReturn(List.of()).when(spyService).getMoviesByGenre("Drama");
//        assertEquals(List.of(), spyService.getMoviesByGenre("Drama"));
//    }

}
