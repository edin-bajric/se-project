package ba.edu.ibu.frent.core.repository;

import ba.edu.ibu.frent.core.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing and managing movie data in the MongoDB database.
 */
@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {

    /**
     * Find movies by title or director, ignoring the case.
     *
     * @param title    The title to search for.
     * @param director The director to search for.
     * @return A list of movies matching the given title or director.
     */
    List<Movie> findByTitleIgnoreCaseContainingOrDirectorIgnoreCaseContaining(String title, String director);
}
