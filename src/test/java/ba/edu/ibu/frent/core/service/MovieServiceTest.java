package ba.edu.ibu.frent.core.service;

import ba.edu.ibu.frent.core.model.Movie;
import ba.edu.ibu.frent.core.repository.MovieRepository;
import ba.edu.ibu.frent.rest.dto.MovieDTO;
import ba.edu.ibu.frent.rest.dto.MovieRequestDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@AutoConfigureMockMvc
@SpringBootTest
class MovieServiceTest {

    @MockBean
    MovieRepository movieRepository;

    @Autowired
    MovieService movieService;

    @Test
    public void shouldReturnMovieWhenCreated() {
        Movie movie = new Movie();
        movie.setId("id");
        movie.setTitle("Title");
        movie.setDirector("Director");
        movie.setYear(2023);

        Mockito.when(movieRepository.save(ArgumentMatchers.any(Movie.class))).thenReturn(movie);
        MovieDTO savedMovie = movieService.addMovie(new MovieRequestDTO(movie));
        Assertions.assertThat(movie.getTitle()).isEqualTo(savedMovie.getTitle());
        Assertions.assertThat(movie.getDirector()).isNotNull();
    }

    @Test
    public void shouldReturnMovieById() {
        Movie movie = new Movie();
        movie.setId("id");
        movie.setTitle("Title");
        movie.setDirector("Director");
        movie.setYear(2023);

        Mockito.when(movieRepository.findById("id")).thenReturn(Optional.of(movie));

        MovieDTO foundMovie = movieService.getMovieById("id");
        Assertions.assertThat(foundMovie.getTitle()).isEqualTo("Title");
    }
}