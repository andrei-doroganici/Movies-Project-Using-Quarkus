package com.movies.entity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    private Long id;

    private String title;

    private String director;

    private Integer releaseYear;

    private String genre;

    private Integer durationMinutes;

    private Float rating;

    private String plotSummary;

    private String language;

    private String country;

    public Movie(Long id, String title, String director, Integer releaseYear, String genre) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
        this.genre = genre;
    }
}
