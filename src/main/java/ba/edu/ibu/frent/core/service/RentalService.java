package ba.edu.ibu.frent.core.service;

import ba.edu.ibu.frent.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.frent.core.model.Movie;
import ba.edu.ibu.frent.core.model.Rental;
import ba.edu.ibu.frent.core.model.User;
import ba.edu.ibu.frent.core.repository.MovieRepository;
import ba.edu.ibu.frent.core.repository.RentalRepository;
import ba.edu.ibu.frent.core.repository.UserRepository;
import ba.edu.ibu.frent.rest.dto.RentalDTO;
import ba.edu.ibu.frent.rest.dto.RentalRequestDTO;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * Service class for managing rentals.
 */
@Service
public class RentalService {
    private final RentalRepository rentalRepository;
    private final MovieRepository movieRepository;
    private final MongoTemplate mongoTemplate;
    private final NotificationService notificationService;
    private final UserRepository userRepository;

    /**
     * Constructor for RentalService.
     *
     * @param rentalRepository     The repository for managing rentals.
     * @param movieRepository      The repository for managing movies.
     * @param mongoTemplate        The MongoDB template for advanced queries.
     * @param notificationService  The service for handling notifications.
     * @param userRepository        The repository for managing users.
     */
    public RentalService(RentalRepository rentalRepository, MovieRepository movieRepository, MongoTemplate mongoTemplate, NotificationService notificationService, UserRepository userRepository) {
        this.rentalRepository = rentalRepository;
        this.movieRepository = movieRepository;
        this.mongoTemplate = mongoTemplate;
        this.notificationService = notificationService;
        this.userRepository = userRepository;
    }

    /**
     * Get a list of all rentals.
     *
     * @return List of RentalDTOs representing all rentals.
     */
    public List<RentalDTO> getRentals() {
        List<Rental> rentals = rentalRepository.findAll();

        return rentals
                .stream()
                .map(RentalDTO::new)
                .collect(toList());
    }

    /**
     * Get a rental by its ID.
     *
     * @param id The ID of the rental to retrieve.
     * @return RentalDTO representing the requested rental.
     * @throws ResourceNotFoundException If the rental with the given ID does not exist.
     */
    public RentalDTO getRentalById(String id) {
        Optional<Rental> rental = rentalRepository.findById(id);
        if (rental.isEmpty()) {
            throw new ResourceNotFoundException("The rental with the given ID does not exist.");
        }
        return new RentalDTO(rental.get());
    }

    /**
     * Add a new rental.
     *
     * @param payload RentalRequestDTO containing information for the new rental.
     * @return RentalDTO representing the newly created rental.
     */
    public RentalDTO addRental(RentalRequestDTO payload) {
        Rental rental = rentalRepository.save(payload.toEntity());
        return new RentalDTO(rental);
    }

    /**
     * Update an existing rental.
     *
     * @param id      The ID of the rental to update.
     * @param payload RentalRequestDTO containing updated information for the rental.
     * @return RentalDTO representing the updated rental.
     * @throws ResourceNotFoundException If the rental with the given ID does not exist.
     */
    public RentalDTO updateRental(String id, RentalRequestDTO payload) {
        Optional<Rental> rental = rentalRepository.findById(id);
        if (rental.isEmpty()) {
            throw new ResourceNotFoundException("The rental with the given ID does not exist.");
        }
        Rental updatedRental = payload.toEntity();
        updatedRental.setId(rental.get().getId());
        updatedRental = rentalRepository.save(updatedRental);
        return new RentalDTO(updatedRental);
    }

    /**
     * Delete a rental by its ID.
     *
     * @param id The ID of the rental to delete.
     */
    public void deleteRental(String id) {
        Optional<Rental> rental = rentalRepository.findById(id);
        rental.ifPresent(rentalRepository::delete);
    }

    /**
     * Return a rental by marking it as returned with the current date.
     *
     * @param id The ID of the rental to be returned.
     * @return RentalDTO representing the updated rental.
     * @throws ResourceNotFoundException If the rental with the given ID does not exist.
     */
    public RentalDTO returnRental(String id) {
        Optional<Rental> rental = rentalRepository.findById(id);
        if (rental.isEmpty()) {
            throw new ResourceNotFoundException("The rental with the given ID does not exist.");
        }
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update()
                .set("returnDate", LocalDate.now())
                .set("returned", true);
        mongoTemplate.updateFirst(query, update, Rental.class);
        Optional<Rental> updatedRental = rentalRepository.findById(id);
        return updatedRental.map(RentalDTO::new).orElseThrow(() ->
                new ResourceNotFoundException("Unable to retrieve the updated rental.")
        );
    }

    /**
     * Get all rentals for a specific user and return overdue rentals.
     *
     * @param username The username of the user.
     * @return List of RentalDTOs representing all rentals for the user.
     */
    public List<RentalDTO> getRentalsForUser(String username) {
        Query query = new Query(Criteria.where("username").is(username));
        List<Rental> rentals = mongoTemplate.find(query, Rental.class);

        LocalDate today = LocalDate.now();

        rentals.forEach(rental -> {
            if (rental.getDueDate().isBefore(today) && rental.getReturnDate() == null && !rental.isReturned()) {
                returnRental(rental.getId());
            }
        });

        return rentals.stream()
                .map(RentalDTO::new)
                .collect(toList());
    }

    /**
     * Add a new rental for a specific user.
     *
     * @param username The username of the user.
     * @param movieId  The ID of the movie to be rented.
     * @param payload  RentalRequestDTO containing information for the new rental.
     * @return RentalDTO representing the newly created rental.
     * @throws IllegalStateException         If the user already has an active rental for the movie or if the movie is not available.
     * @throws ResourceNotFoundException     If the movie with the given ID is not found.
     */
    public RentalDTO addRentalForUser(String username, String movieId, RentalRequestDTO payload) {
        boolean alreadyRented = rentalRepository.existsByMovieIdAndUsernameAndReturnDateIsNull(movieId, username);
        if (alreadyRented) {
            throw new IllegalStateException("You already have an active rental for this movie.");
        }
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie with ID " + movieId + " not found."));
        if (!movie.isAvailable()) {
            throw new IllegalStateException("The movie is not available for rental.");
        }
        double rentalPrice = movie.getRentalPrice();
        String video = movie.getVideo();
        payload.setRentalPrice(rentalPrice);
        payload.setVideo(video);
        payload.setUsername(username);
        payload.setMovieId(movie.getId());
        Rental rental = rentalRepository.save(payload.toEntity());
        return new RentalDTO(rental);
    }

    /**
     * Get a rental by its ID for a specific user.
     *
     * @param id       The ID of the rental to retrieve.
     * @param username The username of the user.
     * @return RentalDTO representing the requested rental.
     * @throws ResourceNotFoundException If the rental with the given ID for the user does not exist.
     */
    public RentalDTO getRentalByIdForUser(String id, String username) {
        Query query = new Query(Criteria.where("_id").is(id).and("username").is(username));
        Rental rental = mongoTemplate.findOne(query, Rental.class);

        if (rental == null) {
            throw new ResourceNotFoundException("The rental with the given ID for the user does not exist.");
        }

        return new RentalDTO(rental);
    }

    /**
     * Delete a rental by its ID for a specific user.
     *
     * @param id       The ID of the rental to delete.
     * @param username The username of the user.
     * @throws ResourceNotFoundException If the rental with the given ID for the user does not exist.
     */
    public void deleteRentalForUser(String id, String username) {
        Query query = new Query(Criteria.where("_id").is(id).and("username").is(username));
        Rental rental = mongoTemplate.findOne(query, Rental.class);

        if (rental == null) {
            throw new ResourceNotFoundException("The rental with the given ID for the user does not exist.");
        }

        rentalRepository.delete(rental);
    }

    /**
     * Check due dates for rentals and send overdue and upcoming due warnings to users.
     * Overdue warnings are sent for rentals that are past their due date.
     * Upcoming due warnings are sent for rentals that will expire in the specified days.
     */
    public void checkDueDatesAndSendWarnings() {
        LocalDate today = LocalDate.now();
        List<Rental> overdueRentals = getOverdueRentals(today);
        for (Rental rental : overdueRentals) {
            sendOverdueWarning(rental);
        }
        List<Rental> upcomingDueRentals = getUpcomingDueRentals(today, 3, 2, 1);
        for (Rental rental : upcomingDueRentals) {
            sendUpcomingDueWarning(rental);
        }
    }

    /**
     * Send an overdue warning to the user for a specific rental.
     *
     * @param rental The overdue rental.
     */
    private void sendOverdueWarning(Rental rental) {
        String username = rental.getUsername();
        String movieTitle = getMovieTitle(rental.getMovieId());
        String warningMessage = "Hello, " + username + "! Your rental for " + movieTitle + " is overdue. Please return it as soon as possible. Thank you!";
        notificationService.sendMessage(username, warningMessage);
    }

    /**
     * Send an upcoming due warning to the user for a specific rental.
     *
     * @param rental The upcoming due rental.
     */
    private void sendUpcomingDueWarning(Rental rental) {
        String username = rental.getUsername();
        String movieTitle = getMovieTitle(rental.getMovieId());
        int daysUntilDue = calculateDaysUntilDue(rental.getDueDate());
        String dayOrDays = (daysUntilDue == 1) ? "day" : "days";
        String warningMessage = "Hello, " + username + "! Your rental for " + movieTitle + " will expire in " + daysUntilDue + " " + dayOrDays + ". Please return it soon. Thank you!";
        notificationService.sendMessage(username, warningMessage);
    }

    /**
     * Get overdue rentals based on the current date.
     *
     * @param today The current date.
     * @return List of overdue rentals.
     */
    private List<Rental> getOverdueRentals(LocalDate today) {
        Query query = new Query(Criteria.where("returnDate").is(null).and("dueDate").lt(today));
        return mongoTemplate.find(query, Rental.class);
    }

    /**
     * Get upcoming due rentals within specified days based on the current date.
     *
     * @param today The current date.
     * @param days  The number of days for upcoming due rentals.
     * @return List of upcoming due rentals.
     */
    private List<Rental> getUpcomingDueRentals(LocalDate today, int... days) {
        List<Rental> upcomingDueRentals = new ArrayList<>();
        for (int day : days) {
            LocalDate dueDateThreshold = today.plusDays(day);
            Query query = new Query(Criteria.where("returnDate").is(null).and("dueDate").is(dueDateThreshold));
            upcomingDueRentals.addAll(mongoTemplate.find(query, Rental.class));
        }
        return upcomingDueRentals;
    }

    /**
     * Get the title of a movie by its ID.
     *
     * @param movieId The ID of the movie.
     * @return The title of the movie or "Unknown Title" if not found.
     */
    private String getMovieTitle(String movieId) {
        Query query = new Query(Criteria.where("_id").is(movieId));
        Movie movie = mongoTemplate.findOne(query, Movie.class);
        return (movie != null) ? movie.getTitle() : "Unknown Title";
    }

    /**
     * Calculate the number of days until the specified due date.
     *
     * @param dueDate The due date of the rental.
     * @return The number of days until the due date.
     */
    private int calculateDaysUntilDue(LocalDate dueDate) {
        LocalDate today = LocalDate.now();
        return (int) ChronoUnit.DAYS.between(today, dueDate);
    }

    /**
     * Get the total amount spent on rentals by a specific user.
     *
     * @param username The username of the user.
     * @return The total amount spent on rentals.
     */
    public double getTotalSpentOnRentals(String username) {
        List<RentalDTO> rentals = getRentalsForUser(username);
        double totalSpent = 0;
        for (RentalDTO rental : rentals) {
            totalSpent += rental.getRentalPrice();
        }

        return totalSpent;
    }

    /**
     * Get the total amount spent on rentals by a specific user.
     *
     * @param id The ID of the user.
     * @return The total amount spent on rentals.
     */
    public double getTotalSpentOnRentalsById(String id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User with ID " + id + " not found.");
        }
        List<RentalDTO> rentals = getRentalsForUser(userOptional.get().getUsername());
        double totalSpent = 0;
        for (RentalDTO rental : rentals) {
            totalSpent += rental.getRentalPrice();
        }

        return totalSpent;
    }

    /**
     * Get all rentals for a specific user by user ID.
     *
     * @param userId The ID of the user.
     * @return List of RentalDTOs representing all rentals for the user.
     * @throws ResourceNotFoundException If the user with the given ID does not exist.
     */
    public List<RentalDTO> getRentalsForUserByUserId(String userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User with ID " + userId + " not found.");
        }
        List<Rental> rentals = rentalRepository.findAllByUsername(userOptional.get().getUsername());
        return rentals.stream()
                .map(RentalDTO::new)
                .collect(toList());
    }
}
