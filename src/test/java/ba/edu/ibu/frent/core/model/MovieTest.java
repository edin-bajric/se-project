package ba.edu.ibu.frent.core.model;

import ba.edu.ibu.frent.core.model.enums.Genre;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {

    @Test
    void shouldCreateNewMovie() {
        Movie movie = new Movie("id", "Title", "Description", "SmallImage", "BigImage", "Director", List.of(Genre.ADVENTURE), 2023, true, 5.99, "link");

        assertEquals("id", movie.getId());
        assertEquals("Title", movie.getTitle());
        assertEquals("Description", movie.getDescription());
        assertEquals("SmallImage", movie.getSmallImage());
        assertEquals("BigImage", movie.getBigImage());
        assertEquals("Director", movie.getDirector());
        assertEquals(List.of(Genre.ADVENTURE), movie.getGenre());
        assertEquals(2023, movie.getYear());
        assertTrue(movie.isAvailable());
        assertEquals(5.99, movie.getRentalPrice());
    }
    @Test
    void shouldCompareTwoMovies() {
        Movie movie1 = new Movie("id", "Title", "Description", "SmallImage", "BigImage", "Director", List.of(Genre.ADVENTURE), 2023, true, 5.99, "link");
        Movie movie2 = new Movie("id", "Title", "Description", "SmallImage", "BigImage", "Director", List.of(Genre.ADVENTURE), 2023, true, 5.99, "link");

        AssertionsForInterfaceTypes
                .assertThat(movie1)
                .usingRecursiveComparison()
                .isEqualTo(movie2);
    }
    @Test
    void shouldHandleNullValues() {
        Movie movie = new Movie();
        assertNull(movie.getId());
        assertNull(movie.getTitle());
        assertNull(movie.getDirector());
        assertNull(movie.getGenre());
    }
    @Test
    void shouldSetAndGetValues() {
        Movie movie = new Movie();
        movie.setId("id");
        movie.setTitle("Title");
        movie.setDirector("Director");
        movie.setGenre(List.of(Genre.COMEDY));
        movie.setYear(2023);
        movie.setAvailable(false);
        movie.setRentalPrice(7.99);

        assertEquals("id", movie.getId());
        assertEquals("Title", movie.getTitle());
        assertEquals("Director", movie.getDirector());
        assertEquals(List.of(Genre.COMEDY), movie.getGenre());
        assertEquals(2023, movie.getYear());
        assertFalse(movie.isAvailable());
        assertEquals(7.99, movie.getRentalPrice());
    }
    @Test
    void shouldNotAllowNegativeYear() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Movie("id", "Title", "Description", "SmallImage", "BigImage", "Director", List.of(Genre.ACTION), -2023, true, 5.99, "link");
        });
    }
    @Test
    void shouldNotAllowNegativeRentalPrice() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Movie("id", "Title", "Description", "SmallImage", "BigImage", "Director", List.of(Genre.ACTION), 2023, true, -5.99, "link");
        });
    }

}