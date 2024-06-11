package ba.edu.ibu.frent.core.repository;

import ba.edu.ibu.frent.core.model.Rental;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RentalRepositoryTest {

    @Autowired
    private RentalRepository rentalRepository;

    @Test
    public void shouldReturnAllRentals() {
        List<Rental> rentals = rentalRepository.findAll();

        assertEquals(5, rentals.size());
        assertEquals("jon", rentals.get(0).getUsername());
    }

    @Test
    public void shouldFindRentalById() {
        Optional<Rental> rental = rentalRepository.findById("655a25068da8c42a66793550");
        assertNotNull(rental.orElse(null));
    }
}