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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
@ExtendWith(MockitoExtension.class)
public class SomeExternalServiceTest {

    @InjectMock
    MovieService movieService;
    @Inject
    SomeExternalService someExternalService;
    private Movie movie1;
    private Movie movie2;
    List<Movie> movies;

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

        movies = List.of(movie1, movie2);
    }

    @Test
    public void testGetMovieByIdReturnsSameMovie() {
        List<Movie> movies = List.of(movie1, movie2);
        Mockito.when(movieService.getAllMovies()).thenReturn(movies);

        Movie testMovie1 = someExternalService.getMovieById(1L);
        Movie testMovie2 = someExternalService.getMovieById(2L);

        assertNotEquals(testMovie1.getTitle(), testMovie2.getTitle());
    }

    @Test
    public void testGetMovieByIdReturnsDifferentMoviesForDifferentArgs(){

        Mockito.when(movieService.getAllMovies()).thenReturn(List.of(movie1, movie2));

        Movie mov1 = someExternalService.getMovieById(movie1.getId());
        Movie mov2 = someExternalService.getMovieById(movie2.getId());

        assertNotNull(mov1);
        assertNotNull(mov2);
        assertNotEquals(mov1.getId(), mov2.getId());
    }

    @Test
    public void testGetMovieByTitleThrowsNullPointerException() {

        Mockito.when(movieService.getAllMovies()).thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class, () -> movieService.getAllMovies());
    }

    @Test
    public void testServiceReturnsDefinedValuesExceptInOneTest(){

        Movie movie3 = Movie.builder().id(3L).title("testTitle3").build();
        List<Movie> expectedMovies = List.of(movie1,movie2,movie3);

        SomeExternalService spyService = Mockito.spy(someExternalService);

        Mockito.when(spyService.getAllMoviesSortedAscByYear())
                .thenReturn(expectedMovies)
                .thenReturn(expectedMovies)
                .thenReturn(List.of());

        for(int i = 0; i < 2; i++){
            List<Movie> actualMovies = spyService.getAllMoviesSortedAscByYear();
            assertEquals(expectedMovies, actualMovies);
        }

        List<Movie> actualMovies = spyService.getAllMoviesSortedAscByYear();
        assertEquals(actualMovies, List.of());

    }
}
