package ba.edu.ibu.frent.core.repository;

import ba.edu.ibu.frent.core.model.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void shouldReturnAllMovies() {
        List<Movie> movies = movieRepository.findAll();

        assertEquals(27, movies.size());
        assertEquals("The Godfather", movies.get(0).getTitle());
    }

    @Test
    public void shouldFindMovieById() {
        Optional<Movie> movie = movieRepository.findById("6557ec1f34a4f00d41452ae1");
        assertNotNull(movie.orElse(null));
    }
}