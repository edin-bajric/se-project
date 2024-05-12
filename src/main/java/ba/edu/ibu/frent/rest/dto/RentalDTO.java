package ba.edu.ibu.frent.rest.dto;

import ba.edu.ibu.frent.core.model.Rental;

import java.time.LocalDate;

/**
 * RentalDTO represents the data transfer object for displaying Rental information.
 */
public class RentalDTO {
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
     * Constructs an empty RentalDTO.
     */
    public RentalDTO() {
    }

    /**
     * Constructs a RentalDTO based on the provided Rental entity.
     *
     * @param rental The Rental entity from which to construct the DTO.
     */
    public RentalDTO(Rental rental) {
        this.id = rental.getId();
        this.username = rental.getUsername();
        this.movieId = rental.getMovieId();
        this.rentalDate = rental.getRentalDate();
        this.dueDate = rental.getDueDate();
        this.returnDate = rental.getReturnDate();
        this.rentalPrice = rental.getRentalPrice();
        this.returned = rental.isReturned();
        this.video = rental.getVideo();
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
