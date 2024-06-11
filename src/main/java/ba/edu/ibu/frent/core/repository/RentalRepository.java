package ba.edu.ibu.frent.core.repository;

import ba.edu.ibu.frent.core.model.Rental;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing and managing rental data in the MongoDB database.
 */
@Repository
public interface RentalRepository extends MongoRepository<Rental, String> {

    /**
     * Check if there is an active rental for a specific movie and user.
     *
     * @param movieId The ID of the movie.
     * @param username The username of the user.
     * @return True if there is an active rental, false otherwise.
     */
    boolean existsByMovieIdAndUsernameAndReturnDateIsNull(String movieId, String username);

    List<Rental> findAllByUsername(String username);
}
