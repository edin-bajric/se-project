package ba.edu.ibu.frent.core.service;

import ba.edu.ibu.frent.api.impl.mailsender.MailgunSender;
import ba.edu.ibu.frent.core.exceptions.repository.ResourceAlreadyExistsException;
import ba.edu.ibu.frent.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.frent.core.model.Movie;
import ba.edu.ibu.frent.core.model.User;
import ba.edu.ibu.frent.core.repository.MovieRepository;
import ba.edu.ibu.frent.core.repository.UserRepository;
import ba.edu.ibu.frent.rest.dto.MovieDTO;
import ba.edu.ibu.frent.rest.dto.MovieRequestDTO;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing movie-related operations.
 */
@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final MongoTemplate mongoTemplate;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final MailgunSender mailgunSender;

    /**
     * Constructor for MovieService.
     *
     * @param movieRepository      The MovieRepository instance.
     * @param mongoTemplate        The MongoTemplate instance.
     * @param userRepository       The UserRepository instance.
     * @param notificationService  The NotificationService instance.
     * @param mailgunSender        The MailgunSender instance.
     */
    public MovieService(MovieRepository movieRepository, MongoTemplate mongoTemplate, UserRepository userRepository, NotificationService notificationService, MailgunSender mailgunSender) {
        this.movieRepository = movieRepository;
        this.mongoTemplate = mongoTemplate;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
        this.mailgunSender = mailgunSender;
    }

    /**
     * Get a list of all movies.
     * @param page The page number
     * @param size Number of results returned
     * @return List of MovieDTO representing all movies.
     */
    public List<MovieDTO> getMovies(int page, int size) {
        int offset = (page - 1) * size;
        List<Movie> movies = movieRepository.findAll();

        // Reverse the order of movies
        Collections.reverse(movies);

        List<Movie> paginatedMovies = movies.stream().skip(offset).limit(size).toList();

        return paginatedMovies.stream()
                .map(MovieDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Get a movie by its ID.
     *
     * @param id The ID of the movie.
     * @return MovieDTO representing the requested movie.
     * @throws ResourceNotFoundException If the movie with the given ID does not exist.
     */
    public MovieDTO getMovieById(String id) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isEmpty()) {
            throw new ResourceNotFoundException("The movie with the given ID does not exist.");
        }
        return new MovieDTO(movie.get());
    }

    /**
     * Add a new movie.
     *
     * @param payload The MovieRequestDTO containing information about the new movie.
     * @return MovieDTO representing the added movie.
     */
    public MovieDTO addMovie(MovieRequestDTO payload) {
        Movie movie = movieRepository.save(payload.toEntity());
        return new MovieDTO(movie);
    }

    /**
     * Update an existing movie.
     *
     * @param id      The ID of the movie to be updated.
     * @param payload The MovieRequestDTO containing updated information.
     * @return MovieDTO representing the updated movie.
     * @throws ResourceNotFoundException If the movie with the given ID does not exist.
     */
    public MovieDTO updateMovie(String id, MovieRequestDTO payload) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isEmpty()) {
            throw new ResourceNotFoundException("The movie with the given ID does not exist.");
        }
        Movie updatedMovie = payload.toEntity();
        updatedMovie.setId(movie.get().getId());
        updatedMovie = movieRepository.save(updatedMovie);
        return new MovieDTO(updatedMovie);
    }

    /**
     * Delete a movie by its ID.
     *
     * @param id The ID of the movie to be deleted.
     */
    public void deleteMovie(String id) {
        Optional<Movie> movie = movieRepository.findById(id);
        movie.ifPresent(movieRepository::delete);
    }

    /**
     * Set a movie as available for rental.
     *
     * @param id The ID of the movie to be set as available.
     * @return MovieDTO representing the updated movie.
     * @throws ResourceNotFoundException       If the movie with the given ID does not exist.
     * @throws ResourceAlreadyExistsException   If the movie is already available.
     * @throws ResourceNotFoundException       If unable to retrieve the updated movie after setting it as available.
     */
    public MovieDTO setAvailable(String id) {
        Optional<Movie> movieOptional = movieRepository.findById(id);
        if (movieOptional.isEmpty()) {
            throw new ResourceNotFoundException("The movie with the given ID does not exist.");
        }
        Movie movie = movieOptional.get();
        if (movie.isAvailable()) {
            throw new ResourceAlreadyExistsException("The movie is already available.");
        }
        updateAvailability(id, true);
        if (!movie.isAvailable()) {
            List<User> usersWithMovieInWishlist = userRepository.findByWishlistContaining(id);
            for (User user : usersWithMovieInWishlist) {
                String username = user.getUsername();
                String notificationMessage = movie.getTitle() + " from your wishlist is now available!";
                notificationService.sendMessage(username, notificationMessage);
            }
        }
        return movieRepository.findById(id)
                .map(MovieDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException("Unable to retrieve the updated movie."));
    }

    /**
     * Set a movie as unavailable for rental.
     *
     * @param id The ID of the movie to be set as unavailable.
     * @return MovieDTO representing the updated movie.
     * @throws ResourceNotFoundException       If the movie with the given ID does not exist.
     * @throws ResourceAlreadyExistsException   If the movie is already unavailable.
     * @throws ResourceNotFoundException       If unable to retrieve the updated movie after setting it as unavailable.
     */
    public MovieDTO setUnavailable(String id) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isEmpty()) {
            throw new ResourceNotFoundException("The movie with the given ID does not exist.");
        }
        if (!movie.get().isAvailable()) {
            throw new ResourceAlreadyExistsException("The movie is already unavailable.");
        }
        updateAvailability(id, false);
        return movieRepository.findById(id)
                .map(MovieDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException("Unable to retrieve the updated movie."));
    }

    /**
     * Update the availability status of a movie.
     *
     * @param id               The ID of the movie to be updated.
     * @param newAvailability The new availability status (true for available, false for unavailable).
     */
    private void updateAvailability(String id, boolean newAvailability) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update().set("available", newAvailability);
        mongoTemplate.updateFirst(query, update, Movie.class);
    }

    /**
     * Set the price of a movie after applying a discount and notify users in the wishlist.
     *
     * @param id       The ID of the movie to update the price.
     * @param discount The discount percentage to apply.
     * @return MovieDTO representing the updated movie.
     * @throws ResourceNotFoundException     If the movie with the given ID does not exist.
     * @throws ResourceAlreadyExistsException If the movie is already on sale.
     */
    public MovieDTO setPriceAndNotify(String id, double discount) {
        Optional<Movie> movieOptional = movieRepository.findById(id);
        if (movieOptional.isEmpty()) {
            throw new ResourceNotFoundException("The movie with the given ID does not exist.");
        }
        Movie movie = movieOptional.get();
        double originalPrice = movie.getRentalPrice();
        double discountedPrice = calculateDiscountedPrice(originalPrice, discount);
        if (originalPrice == discountedPrice) {
            throw new ResourceAlreadyExistsException("The movie is already on sale.");
        }
        movie.setRentalPrice(discountedPrice);
        movieRepository.save(movie);
        List<String> userEmails = userRepository.findEmailsByWishlistContaining(id);
        String message = movie.getTitle() + " is now on sale! Original price: " +
                originalPrice + ", Discounted price: " + discountedPrice;
        String subject = movie.getTitle() + " from your wishlist is now on sale!";
        mailgunSender.send(userEmails, message, subject);
        List<User> usersWithMovieInWishlist = userRepository.findByWishlistContaining(id);
        for (User user : usersWithMovieInWishlist) {
            String username = user.getUsername();
            notificationService.sendMessage(username, message);
        }
        return new MovieDTO(movie);
    }

    /**
     * Calculate the discounted price of a movie based on the original price and discount percentage.
     *
     * @param originalPrice The original price of the movie.
     * @param discount      The discount percentage to apply.
     * @return The discounted price.
     */
    private double calculateDiscountedPrice(double originalPrice, double discount) {
        double discountedPrice = originalPrice - (originalPrice * (discount / 100));
        return Math.round(discountedPrice * 100.0) / 100.0;
    }

    /**
     * Revert the price of a movie to the original price.
     *
     * @param movieId  The ID of the movie to revert the price.
     * @param oldPrice The original price to set.
     * @return MovieDTO representing the updated movie.
     * @throws ResourceNotFoundException If the movie with the given ID does not exist.
     */
    public MovieDTO revertPrice(String movieId, double oldPrice) {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if (movieOptional.isEmpty()) {
            throw new ResourceNotFoundException("The movie with the given ID does not exist.");
        }
        Movie movie = movieOptional.get();
        movie.setRentalPrice(oldPrice);
        movieRepository.save(movie);
        return new MovieDTO(movie);
    }

    /**
     * Search for movies by title or director that match the given keyword.
     *
     * @param keyword The keyword to search for in movie titles or directors.
     * @param page The page number
     * @param size Number of results returned
     * @return List of MovieDTOs representing the matched movies.
     */
    public List<MovieDTO> searchMovies(String keyword, int page, int size) {
        int offset = (page - 1) * size;
        List<Movie> movies = movieRepository.findByTitleIgnoreCaseContainingOrDirectorIgnoreCaseContaining(keyword, keyword);
        List<Movie> paginatedMovies = movies.stream().skip(offset).limit(size).toList();
        return paginatedMovies.stream()
                .map(MovieDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Check if a given substring matches the beginning of any word in the text or is contained in the text.
     *
     * @param substring The substring to match.
     * @param text      The text to search within.
     * @return True if the substring matches the beginning of any word in the text or is contained in the text, otherwise false.
     */
    private boolean matchesSubstringInWord(String substring, String text) {
        String[] words = text.split("\\s+");
        for (String word : words) {
            if (word.startsWith(substring)) {
                return true;
            }
        }
        return text.toLowerCase().contains(substring.toLowerCase());
    }

    /**
     * Get a list of all movies.
     * @return List of MovieDTO representing all movies.
     */
    public List<MovieDTO> getAllMovies () {
        List<Movie> movies = movieRepository.findAll();
        Collections.reverse(movies);
        return movies.stream()
                .map(MovieDTO::new)
                .collect(Collectors.toList());
    }
}
