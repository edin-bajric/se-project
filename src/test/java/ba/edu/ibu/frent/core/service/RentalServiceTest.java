package ba.edu.ibu.frent.core.service;

import ba.edu.ibu.frent.core.model.Rental;
import ba.edu.ibu.frent.core.repository.RentalRepository;
import ba.edu.ibu.frent.rest.dto.RentalDTO;
import ba.edu.ibu.frent.rest.dto.RentalRequestDTO;
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
class RentalServiceTest {

    @MockBean
    RentalRepository rentalRepository;

    @Autowired
    RentalService rentalService;

    @Test
    public void shouldReturnRentalWhenCreated() {
        Rental rental = new Rental();
        rental.setId("id");
        rental.setUsername("username");
        rental.setMovieId("movieId");
        rental.setRentalPrice(5.99);

        Mockito.when(rentalRepository.save(ArgumentMatchers.any(Rental.class))).thenReturn(rental);
        RentalDTO savedRental = rentalService.addRental(new RentalRequestDTO(rental));
        Assertions.assertThat(rental.getUsername()).isEqualTo(savedRental.getUsername());
        Assertions.assertThat(rental.getUsername()).isNotNull();
    }

    @Test
    public void shouldReturnRentalById() {
        Rental rental = new Rental();
        rental.setId("id");
        rental.setUsername("username");
        rental.setMovieId("movieId");
        rental.setRentalPrice(5.99);

        Mockito.when(rentalRepository.findById("id")).thenReturn(Optional.of(rental));
        RentalDTO foundRental = rentalService.getRentalById("id");
        Assertions.assertThat(foundRental.getId()).isEqualTo("id");
    }
}