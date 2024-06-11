package ba.edu.ibu.frent.core.repository;

import ba.edu.ibu.frent.core.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing and managing user data in the MongoDB database.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Find the first user whose email matches the given pattern.
     *
     * @param emailPattern The email pattern to search for.
     * @return An optional containing the user if found, otherwise empty.
     */
    Optional<User> findFirstByEmailLike(String emailPattern);

    /**
     * Find a user by username or email.
     *
     * @param usernameOrEmail The username or email to search for.
     * @return An optional containing the user if found, otherwise empty.
     */
    @Query(value="{$or:[{email:'?0'}, {username:'?0'}]}")
    Optional<User> findByUsernameOrEmail(String usernameOrEmail);

    /**
     * Find a user by email.
     *
     * @param email The email to search for.
     * @return An optional containing the user if found, otherwise empty.
     */
    Optional<User> findByEmail(String email);

    /**
     * Check if a user with the given email exists.
     *
     * @param email The email to check.
     * @return True if a user with the email exists, false otherwise.
     */
    boolean existsByEmail(String email);

    /**
     * Check if a user with the given username exists.
     *
     * @param username The username to check.
     * @return True if a user with the username exists, false otherwise.
     */
    boolean existsByUsername(String username);

    /**
     * Find users with a movie in their wishlist.
     *
     * @param id The ID of the movie.
     * @return A list of users with the movie in their wishlist.
     */
    List<User> findByWishlistContaining(String id);

    /**
     * Find emails of users with a movie in their wishlist.
     *
     * @param id The ID of the movie.
     * @return A list of emails of users with the movie in their wishlist.
     */
    @Query(value = "{'wishlist': ?0}", fields = "{'email': 1, '_id': 0}")
    List<String> findEmailsByWishlistContaining(String id);
}
