package ba.edu.ibu.frent.rest.controllers;

import ba.edu.ibu.frent.core.service.MovieService;
import ba.edu.ibu.frent.rest.dto.MovieDTO;
import ba.edu.ibu.frent.rest.dto.MovieRequestDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling movie-related operations.
 */
@RestController
@RequestMapping("api/movies")
@SecurityRequirement(name = "JWT Security")
public class MovieController {
    private final MovieService movieService;

    /**
     * Constructs a MovieController with the provided MovieService.
     *
     * @param movieService The MovieService for handling movie-related operations.
     */
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    /**
     * Retrieves a list of all movies.
     * @param page The page number
     * @param size Number of results returned
     * @return ResponseEntity with a list of MovieDTOs.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/")
    public ResponseEntity<List<MovieDTO>> getMovies(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(movieService.getMovies(page, size));
    }

    /**
     * Adds a new movie.
     *
     * @param movie The MovieRequestDTO containing information about the new movie.
     * @return ResponseEntity with the added movie details.
     */
    @RequestMapping(method = RequestMethod.POST, path = "/add")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<MovieDTO> add(@RequestBody MovieRequestDTO movie) {
        return ResponseEntity.ok(movieService.addMovie(movie));
    }

    /**
     * Retrieves details of a specific movie by ID.
     *
     * @param id The ID of the movie to retrieve.
     * @return ResponseEntity with the details of the requested movie.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable String id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    /**
     * Updates details of a specific movie by ID.
     *
     * @param id    The ID of the movie to update.
     * @param movie The MovieRequestDTO containing updated information.
     * @return ResponseEntity with the updated movie details.
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable String id, @RequestBody MovieRequestDTO movie) {
        return ResponseEntity.ok(movieService.updateMovie(id, movie));
    }

    /**
     * Deletes a movie by ID.
     *
     * @param id The ID of the movie to delete.
     * @return ResponseEntity with no content.
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<Void> deleteMovie(@PathVariable String id) {
        movieService.deleteMovie(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Searches for movies based on a keyword.
     *
     * @param keyword The keyword to search for in movie titles or directors.
     * @param page The page number
     * @param size Number of results returned
     * @return ResponseEntity with a list of matching MovieDTOs.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/search/{keyword}/{page}/{size}")
    public ResponseEntity<List<MovieDTO>> searchMovies(@PathVariable String keyword, @PathVariable int page, @PathVariable int size) {
        return ResponseEntity.ok(movieService.searchMovies(keyword, page, size));
    }

    /**
     * Sets a movie as available for rent.
     *
     * @param id The ID of the movie to set as available.
     * @return ResponseEntity with the updated movie details.
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/setAvailable/{id}")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<MovieDTO> setAvailable(@PathVariable String id) {
        return ResponseEntity.ok(movieService.setAvailable(id));
    }

    /**
     * Sets a movie as unavailable for rent.
     *
     * @param id The ID of the movie to set as unavailable.
     * @return ResponseEntity with the updated movie details.
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/setUnavailable/{id}")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<MovieDTO> setUnavailable(@PathVariable String id) {
        return ResponseEntity.ok(movieService.setUnavailable(id));
    }

    /**
     * Sets a discounted price for a movie and notifies users.
     *
     * @param id       The ID of the movie to set the discount for.
     * @param discount The discount percentage.
     * @return ResponseEntity with the updated movie details.
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/discount/{id}/{discount}")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<MovieDTO> setPriceAndNotify(@PathVariable String id, @PathVariable double discount) {
        MovieDTO updatedMovie = movieService.setPriceAndNotify(id, discount);
        return new ResponseEntity<>(updatedMovie, HttpStatus.OK);
    }

    /**
     * Reverts a movie's price to its original value.
     *
     * @param id      The ID of the movie to revert the price for.
     * @param oldPrice The original price to revert to.
     * @return ResponseEntity with the updated movie details.
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/revertPrice/{id}/{oldPrice}")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<MovieDTO> revertPrice(@PathVariable String id, @PathVariable double oldPrice) {
        MovieDTO updatedMovie = movieService.revertPrice(id, oldPrice);
        return new ResponseEntity<>(updatedMovie, HttpStatus.OK);
    }
}

