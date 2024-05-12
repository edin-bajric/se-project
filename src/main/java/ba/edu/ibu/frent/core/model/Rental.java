package ba.edu.ibu.frent.core.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

/**
 * Represents a rental transaction in the system.
 * Each rental has a unique identifier, associated username, movie ID, rental date, due date,
 * return date, rental price, return status and video;
 */
@Document
public class Rental {
    @Id
    private String id;
    private String username;
    private String movieId;
    private LocalDate rentalDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private double rentalPrice;
    private boolean returned;
    private String video;

    /**
     * Default constructor for the Rental class.
     * Initializes the rental transaction with default values.
     */
    public Rental() {
    }

    /**
     * Parameterized constructor for the Rental class.
     * Initializes the rental transaction with the provided values.
     *
     * @param id           The unique identifier of the rental transaction.
     * @param username     The username associated with the rental.
     * @param movieId      The unique identifier of the rented movie.
     * @param rentalDate   The date when the movie is rented.
     * @param dueDate      The due date for returning the rented movie.
     * @param returnDate   The date when the movie is returned.
     * @param rentalPrice  The rental price associated with the transaction.
     * @param returned     The return status of the rental transaction.
     * @param video        The video associated with the rental transaction.
     *
     * @throws IllegalArgumentException If the provided rental price is negative.
     */
    public Rental(String id, String username, String movieId, LocalDate rentalDate, LocalDate dueDate, LocalDate returnDate, double rentalPrice, boolean returned, String video) {
        this.id = id;
        this.username = username;
        this.movieId = movieId;
        this.rentalDate = rentalDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        if (rentalPrice < 0) {
            throw new IllegalArgumentException("Rental price cannot be negative");
        }
        this.rentalPrice = rentalPrice;
        this.returned = returned;
        this.video = video;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public LocalDate getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public double getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(double rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
