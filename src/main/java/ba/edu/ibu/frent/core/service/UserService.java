package ba.edu.ibu.frent.core.service;

import ba.edu.ibu.frent.core.api.mailsender.MailSender;
import ba.edu.ibu.frent.core.exceptions.auth.UserAlreadyExistsException;
import ba.edu.ibu.frent.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.frent.core.model.Movie;
import ba.edu.ibu.frent.core.model.User;
import ba.edu.ibu.frent.core.repository.MovieRepository;
import ba.edu.ibu.frent.core.repository.UserRepository;
import ba.edu.ibu.frent.rest.dto.UserDTO;
import ba.edu.ibu.frent.rest.dto.UserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Service class that handles operations related to user management.
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    @Autowired
    private MailSender mailgunSender;

    /**
     * Constructs a UserService with the provided repositories.
     *
     * @param userRepository  The repository for user entities.
     * @param movieRepository The repository for movie entities.
     */
    public UserService(UserRepository userRepository, MovieRepository movieRepository) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    /**
     * Get a list of all users in the system.
     *
     * @return List of UserDTO representing all users.
     */
    public List<UserDTO> getUsers() {
        List<User> users = userRepository.findAll();

        return users
                .stream()
                .map(UserDTO::new)
                .collect(toList());
    }

    /**
     * Get a user by their unique identifier.
     *
     * @param id The ID of the user.
     * @return UserDTO representing the user with the specified ID.
     * @throws ResourceNotFoundException If the user with the given ID does not exist.
     */
    public UserDTO getUserById(String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("The user with the given ID does not exist.");
        }
        return new UserDTO(user.get());
    }

    /**
     * Add a new user to the system.
     *
     * @param payload The user information to be added.
     * @return UserDTO representing the added user.
     * @throws UserAlreadyExistsException If a user with the same email or username already exists.
     */
    public UserDTO addUser(UserRequestDTO payload) {

        if (userRepository.existsByEmail(payload.getEmail()) || userRepository.existsByUsername(payload.getUsername())) {
            throw new UserAlreadyExistsException("User already exists");
        }

        User user = userRepository.save(payload.toEntity());
        return new UserDTO(user);
    }

    /**
     * Update an existing user's information.
     *
     * @param id      The ID of the user to be updated.
     * @param payload The updated user information.
     * @return UserDTO representing the updated user.
     * @throws ResourceNotFoundException If the user with the given ID does not exist.
     */
    public UserDTO updateUser(String id, UserRequestDTO payload) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("The user with the given ID does not exist.");
        }
        User updatedUser = payload.toEntity();
        updatedUser.setId(user.get().getId());
        updatedUser = userRepository.save(updatedUser);
        return new UserDTO(updatedUser);
    }

    /**
     * Delete a user from the system.
     *
     * @param id The ID of the user to be deleted.
     */
    public void deleteUser(String id) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(userRepository::delete);
    }

    /**
     * Filter user by email.
     *
     * @param email The email to filter by.
     * @return UserDTO representing the user with the specified email, or null if not found.
     */
    public UserDTO filterByEmail(String email) {
        Optional<User> user = userRepository.findFirstByEmailLike(email);
        return user.map(UserDTO::new).orElse(null);
    }

    /**
     * Custom implementation of UserDetailsService for authentication.
     *
     * @return UserDetailsService implementation.
     */
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByUsernameOrEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    /**
     * Add a movie to the user's cart.
     *
     * @param movieId  The ID of the movie to be added to the cart.
     * @param username The username of the user.
     * @return UserDTO representing the updated user with the added movie in the cart.
     * @throws ResourceNotFoundException If the user or movie with the given ID does not exist.
     * @throws IllegalStateException     If the movie is not available.
     */
    public UserDTO addToCart(String movieId, String username) {
        Optional<User> userOptional = userRepository.findByUsernameOrEmail(username);
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("The user with the given username does not exist.");
        }
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if (movieOptional.isEmpty()) {
            throw new ResourceNotFoundException("The movie with the given ID does not exist.");
        }
        Movie movie = movieOptional.get();
        if (!movie.isAvailable()) {
            throw new IllegalStateException("The movie is not available.");
        }
        User user = userOptional.get();
        Set<String> cart = user.getCart();
        cart.add(movieId);
        user.setCart(cart);
        User updatedUser = userRepository.save(user);
        return new UserDTO(updatedUser);
    }

    /**
     * Add a movie to the user's wishlist.
     *
     * @param movieId  The ID of the movie to be added to the wishlist.
     * @param username The username of the user.
     * @return UserDTO representing the updated user with the added movie in the wishlist.
     * @throws ResourceNotFoundException If the user with the given username does not exist.
     */
    public UserDTO addToWishlist(String movieId, String username) {
        Optional<User> userOptional = userRepository.findByUsernameOrEmail(username);
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("The user with the given username does not exist.");
        }
        User user = userOptional.get();
        Set<String> wishlist = user.getWishlist();
        wishlist.add(movieId);
        user.setWishlist(wishlist);
        User updatedUser = userRepository.save(user);
        return new UserDTO(updatedUser);
    }

    /**
     * Remove a movie from the user's cart.
     *
     * @param movieId  The ID of the movie to be removed from the cart.
     * @param username The username of the user.
     * @return UserDTO representing the updated user with the removed movie from the cart.
     * @throws ResourceNotFoundException If the user with the given username does not exist.
     */
    public UserDTO removeFromCart(String movieId, String username) {
        Optional<User> userOptional = userRepository.findByUsernameOrEmail(username);
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("The user with the given username does not exist.");
        }
        User user = userOptional.get();
        Set<String> cart = user.getCart();
        cart.remove(movieId);
        user.setCart(cart);
        User updatedUser = userRepository.save(user);
        return new UserDTO(updatedUser);
    }

    /**
     * Remove a movie from the user's wishlist.
     *
     * @param movieId  The ID of the movie to be removed from the wishlist.
     * @param username The username of the user.
     * @return UserDTO representing the updated user with the removed movie from the wishlist.
     * @throws ResourceNotFoundException If the user with the given username does not exist.
     */
    public UserDTO removeFromWishlist(String movieId, String username) {
        Optional<User> userOptional = userRepository.findByUsernameOrEmail(username);
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("The user with the given username does not exist.");
        }
        User user = userOptional.get();
        Set<String> wishlist = user.getWishlist();
        wishlist.remove(movieId);
        user.setWishlist(wishlist);
        User updatedUser = userRepository.save(user);
        return new UserDTO(updatedUser);
    }

    /**
     * Get the movies in the user's cart.
     *
     * @param username The username of the user.
     * @return A set of movie IDs representing the movies in the user's cart.
     * @throws ResourceNotFoundException If the user with the given username does not exist.
     */
    public Set<String> getCart(String username) {
        Optional<User> userOptional = userRepository.findByUsernameOrEmail(username);
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("The user with the given username does not exist.");
        }
        User user = userOptional.get();
        return user.getCart() != null ? new HashSet<>(user.getCart()) : new HashSet<>();
    }

    /**
     * Get the movies in the user's wishlist.
     *
     * @param username The username of the user.
     * @return A set of movie IDs representing the movies in the user's wishlist.
     * @throws ResourceNotFoundException If the user with the given username does not exist.
     */
    public Set<String> getWishlist(String username) {
        Optional<User> userOptional = userRepository.findByUsernameOrEmail(username);
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("The user with the given username does not exist.");
        }
        User user = userOptional.get();
        return user.getWishlist() != null ? new HashSet<>(user.getWishlist()) : new HashSet<>();
    }

    /**
     * Calculate the total cost of movies in the user's cart.
     *
     * @param username The username of the user.
     * @return The total cost of movies in the user's cart.
     * @throws ResourceNotFoundException If the user with the given username does not exist.
     */
    public double getCartTotal(String username) {
        Optional<User> userOptional = userRepository.findByUsernameOrEmail(username);
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("The user with the given username does not exist.");
        }
        User user = userOptional.get();
        Set<String> cart = user.getCart();
        if (cart == null || cart.isEmpty()) {
            return 0.0;
        }
        List<Movie> moviesInCart = movieRepository.findAllById(cart);
        return moviesInCart.stream()
                .mapToDouble(Movie::getRentalPrice)
                .sum();
    }
}
