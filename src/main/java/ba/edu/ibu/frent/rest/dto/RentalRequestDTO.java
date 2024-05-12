package ba.edu.ibu.frent.rest.dto;

import ba.edu.ibu.frent.core.model.Rental;

import java.time.LocalDate;

/**
 * RentalRequestDTO represents the data transfer object for creating Rental entities.
 */
public class RentalRequestDTO {
    private String username;
    private String movieId;

    private double rentalPrice;
    private String video;

    /**
     * Constructs an empty RentalRequestDTO.
     */
    public RentalRequestDTO() { }

    /**
     * Constructs a RentalRequestDTO based on the provided Rental entity.
     *
     * @param rental The Rental entity from which to construct the DTO.
     */
    public RentalRequestDTO(Rental rental) {
        this.username = rental.getUsername();
        this.movieId = rental.getMovieId();
    }

    /**
     * Converts the DTO to a Rental entity.
     *
     * @return A new Rental entity created from the DTO data.
     */
    public Rental toEntity() {
        Rental rental = new Rental();
        rental.setUsername(username);
        rental.setMovieId(movieId);
        rental.setRentalDate(LocalDate.now());
        LocalDate dueDate = LocalDate.now().plusDays(10);
        rental.setDueDate(dueDate);
        rental.setReturnDate(null);
        rental.setRentalPrice(rentalPrice);
        rental.setReturned(false);
        rental.setVideo(video);
        return rental;
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

    public double getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(double rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}