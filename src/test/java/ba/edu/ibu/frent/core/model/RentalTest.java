package ba.edu.ibu.frent.core.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RentalTest {

    @Test
    void shouldCreateNewRental() {
        Rental rental = new Rental("id", "username", "movieId", LocalDate.now(), LocalDate.now().plusDays(10), LocalDate.now(), 5.99, true, "link");

        assertEquals("id", rental.getId());
        assertEquals("username", rental.getUsername());
        assertEquals("movieId", rental.getMovieId());
        assertEquals(LocalDate.now(), rental.getRentalDate());
        assertEquals(LocalDate.now(), rental.getReturnDate());
        assertEquals(5.99, rental.getRentalPrice());
        assertTrue(rental.isReturned());
    }
    @Test
    void shouldNotAllowNegativeRentalPrice() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Rental("id", "username", "movieId", LocalDate.now(), LocalDate.now().plusDays(10), LocalDate.now(), -5.99, true, "link");
        });
    }
    @Test
    void shouldSetAndGetValues() {
        Rental rental = new Rental();

        rental.setId("id");
        rental.setUsername("username");
        rental.setMovieId("movieId");
        rental.setRentalDate(LocalDate.now());
        rental.setReturnDate(LocalDate.now());
        rental.setRentalPrice(5.99);
        rental.setReturned(true);

        assertEquals("id", rental.getId());
        assertEquals("username", rental.getUsername());
        assertEquals("movieId", rental.getMovieId());
        assertEquals(LocalDate.now(), rental.getRentalDate());
        assertEquals(LocalDate.now(), rental.getReturnDate());
        assertEquals(5.99, rental.getRentalPrice());
        assertTrue(rental.isReturned());
    }

}