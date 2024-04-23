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
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
@ExtendWith(MockitoExtension.class)
public class SomeExternalServiceMockitoTest {

    @InjectMock
    MovieService movieService;

    @Inject
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
                .durationMinutes(111)
                .rating(8.8f)
                .plotSummary("Lorem ipsum dolor sit amet")
                .language("English")
                .country("United States")
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
        Mockito.when(movieService.getAllMovies()).thenReturn(movieList);
        Movie expectedMovie = movie1;

        Movie resultMovie1 = someExternalService.getMovieById(1L);
        Movie resultMovie2 = someExternalService.getMovieById(1L);

        assertEquals(expectedMovie, resultMovie1);
        assertEquals(expectedMovie, resultMovie2);
    }

    @Test
    public void testGetMoviesSortedReturnsDifferentListsConsecutively() {
        List<Movie> firstCallMovies = List.of(movie1);
        List<Movie> secondCallMovies = List.of(movie2);

        Mockito.when(movieService.getAllMovies())
                .thenReturn(firstCallMovies, secondCallMovies);

        assertEquals(firstCallMovies, someExternalService.getAllMoviesSortedAscByYear());
        assertEquals(secondCallMovies, someExternalService.getAllMoviesSortedAscByYear());
    }

    @Test
    public void testGetMoviesByGenreBasedOnParameters() {
        Mockito.when(movieService.getAllMovies()).thenReturn(movieList);

        List<Movie> fictionMovies = someExternalService.getMoviesByGenre("Fiction");
        List<Movie> dramaMovies = someExternalService.getMoviesByGenre("Drama");

        assertEquals(1, fictionMovies.size());
        assertEquals(movie1, fictionMovies.get(0));

        assertEquals(2, dramaMovies.size());
        assertEquals(movie2, dramaMovies.get(0));
    }

    @Test
    public void testServiceHandlesExceptionsGracefully() {
        Mockito.when(movieService.getAllMovies()).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> someExternalService.getAllMoviesSortedAscByYear());
    }

    @Test
    public void testSpyServiceModifiesBehaviorSelectively() {
        SomeExternalService spyService = Mockito.spy(someExternalService);
        List<Movie> expectedMovies = movieList;

        Mockito.doReturn(expectedMovies).when(spyService).getAllMoviesSortedAscByYear();

        assertEquals(expectedMovies, spyService.getAllMoviesSortedAscByYear());
        Mockito.verify(spyService, Mockito.times(1)).getAllMoviesSortedAscByYear();
        Mockito.doReturn(List.of()).when(spyService).getAllMoviesSortedAscByYear();
        assertEquals(List.of(), spyService.getAllMoviesSortedAscByYear());
    }

}
