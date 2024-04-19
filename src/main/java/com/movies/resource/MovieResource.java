package com.movies.resource;

import com.movies.entity.Movie;
import com.movies.service.MovieService;
import com.movies.service.SomeExternalService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Path("/movies")
public class MovieResource {

    @Inject
    MovieService movieService;

    @Inject
    SomeExternalService externalService;

    @GET
    public Response getAll() {
        return Response.ok(movieService.getAllMovies()).build();
    }

    @GET
    @Path("{id}")
    public Response getById(@PathParam("id") Long id) {
//        List<Movie> responseMovies = movieService.getAllMovies();
        Movie foundMovie = externalService.getMovieById(id);

        if (foundMovie != null) {
            return Response.ok(foundMovie).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addMovie(Movie movie) {
        Movie savedMovie = movieService.addMovie(movie);
        System.out.println("PUT add movie: " + savedMovie);
        return Response.status(Response.Status.CREATED).entity(savedMovie).build();
    }

    @PUT
    @Path("{id}")
    public Response updateMovie(@PathParam("id") Long id, Movie movie) {
        Movie updatedMovie = movieService.updateMovie(id, movie);
        System.out.println("PUT update movie : " + updatedMovie);

        if (updatedMovie != null) {
            return Response.ok(updatedMovie).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteMovie(@PathParam("id") Long id) {
        boolean deletedMovie = movieService.deleteMovie(id);
        System.out.println("DELETE movie with id: " + id);

        if (deletedMovie) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
