package com.movies.repository;

import com.movies.entity.Movie;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class MovieRepository {

    @ConfigProperty(name = "movies.1.id")
    private Long movie1Id;
    @ConfigProperty(name = "movies.1.title")
    private String movie1Title;
    @ConfigProperty(name = "movies.1.director")
    private String movie1Director;
    @ConfigProperty(name = "movies.1.releaseYear")
    private Integer movie1ReleaseYear;
    @ConfigProperty(name = "movies.1.genre")
    private String movie1Genre;
    @ConfigProperty(name = "movies.1.durationMinutes")
    private Integer movie1DurationMinutes;
    @ConfigProperty(name = "movies.1.rating")
    private Float movie1Rating;
    @ConfigProperty(name = "movies.1.plotSummary")
    private String movie1PlotSummary;
    @ConfigProperty(name = "movies.1.language")
    private String movie1Language;
    @ConfigProperty(name = "movies.1.country")
    private String movie1Country;

    @ConfigProperty(name = "movies.2.id")
    private Long movie2Id;
    @ConfigProperty(name = "movies.2.title")
    private String movie2Title;
    @ConfigProperty(name = "movies.2.director")
    private String movie2Director;
    @ConfigProperty(name = "movies.2.releaseYear")
    private Integer movie2ReleaseYear;
    @ConfigProperty(name = "movies.2.genre")
    private String movie2Genre;
    @ConfigProperty(name = "movies.2.durationMinutes")
    private Integer movie2DurationMinutes;
    @ConfigProperty(name = "movies.2.rating")
    private Float movie2Rating;
    @ConfigProperty(name = "movies.2.plotSummary")
    private String movie2PlotSummary;
    @ConfigProperty(name = "movies.2.language")
    private String movie2Language;
    @ConfigProperty(name = "movies.2.country")
    private String movie2Country;

    @ConfigProperty(name = "movies.3.id")
    private Long movie3Id;
    @ConfigProperty(name = "movies.3.title")
    private String movie3Title;
    @ConfigProperty(name = "movies.3.director")
    private String movie3Director;
    @ConfigProperty(name = "movies.3.releaseYear")
    private Integer movie3ReleaseYear;
    @ConfigProperty(name = "movies.3.genre")
    private String movie3Genre;
    @ConfigProperty(name = "movies.3.durationMinutes")
    private Integer movie3DurationMinutes;
    @ConfigProperty(name = "movies.3.rating")
    private Float movie3Rating;
    @ConfigProperty(name = "movies.3.plotSummary")
    private String movie3PlotSummary;
    @ConfigProperty(name = "movies.3.language")
    private String movie3Language;
    @ConfigProperty(name = "movies.3.country")
    private String movie3Country;

    public List<Movie> getMovies(){
        List<Movie> movies = new ArrayList<>();

        Movie movie1 = new Movie().builder()
                .id(movie1Id)
                .title(movie1Title)
                .director(movie1Director)
                .releaseYear(movie1ReleaseYear)
                .genre(movie1Genre)
                .durationMinutes(movie1DurationMinutes)
                .rating(movie1Rating)
                .plotSummary(movie1PlotSummary)
                .language(movie1Language)
                .country(movie1Country)
                .build();

        Movie movie2 = new Movie().builder()
                .id(movie2Id)
                .title(movie2Title)
                .director(movie2Director)
                .releaseYear(movie2ReleaseYear)
                .genre(movie2Genre)
                .durationMinutes(movie2DurationMinutes)
                .rating(movie2Rating)
                .plotSummary(movie2PlotSummary)
                .language(movie2Language)
                .country(movie2Country)
                .build();

        Movie movie3 = new Movie().builder()
                .id(movie3Id)
                .title(movie3Title)
                .director(movie3Director)
                .releaseYear(movie3ReleaseYear)
                .genre(movie3Genre)
                .durationMinutes(movie3DurationMinutes)
                .rating(movie3Rating)
                .plotSummary(movie3PlotSummary)
                .language(movie3Language)
                .country(movie3Country)
                .build();

        movies.add(movie1);
        movies.add(movie2);
        movies.add(movie3);
        return movies;
    }

}

