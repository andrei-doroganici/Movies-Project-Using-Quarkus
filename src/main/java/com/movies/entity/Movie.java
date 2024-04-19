package com.movies.entity;

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
}
