package com.movies.resource;

import com.movies.entity.Movie;
import com.movies.service.MovieService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@QuarkusTest
@ExtendWith(MockitoExtension.class)
public class MovieResourceTest {

    @InjectMock
    MovieService movieService;
    @Inject
    MovieResource movieResource;

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
    void testGetAllMovies() {
        Mockito.when(movieService.getAllMovies()).thenReturn(movies);
        Response response = movieResource.getAll();

        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertEquals(movies, response.getEntity());
        List<Movie> entities = (List<Movie>) response.getEntity();
        assertFalse(entities.isEmpty());
        verify(movieService).getAllMovies();
    }

    @Test
    void testGetById() {
        Mockito.when(movieService.getAllMovies()).thenReturn(movies);
        Response testMovieResponse1 = movieResource.getById(1L);
        Movie testMovie1 = (Movie) testMovieResponse1.getEntity();

        assertNotNull(testMovieResponse1);
        assertEquals(movie1, testMovie1);
        assertEquals(1L, (long) testMovie1.getId());
        assertEquals(200, testMovieResponse1.getStatus());
        assertEquals(testMovie1, testMovieResponse1.getEntity());
    }
}
