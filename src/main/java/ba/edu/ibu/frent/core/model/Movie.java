package ba.edu.ibu.frent.core.model;

import ba.edu.ibu.frent.core.model.enums.Genre;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Represents a movie entity in the system.
 * Each movie has a unique identifier, title, description, smallImage, bigImage, director, genre, release year, availability status, rental price
 * and video associated with it.
 */
@Document
public class Movie {
    @Id
    private String id;
    private String title;
    private String description;
    private String smallImage;
    private String bigImage;
    private String director;
    private List<Genre> genre;
    private int year;
    private boolean available;
    private double rentalPrice;
    private String video;

    /**
     * Default constructor for the Movie class.
     * Initializes the movie entity with default values.
     */
    public Movie() {
    }

    /**
     * Parameterized constructor for the Movie class.
     * Initializes the movie entity with the provided values.
     *
     * @param id           The unique identifier of the movie.
     * @param title        The title of the movie.
     * @param description  The description of the movie.
     * @param smallImage   The small image of the movie.
     * @param bigImage     The big image of the movie.
     * @param director     The director of the movie.
     * @param genre        The list of genres associated with the movie.
     * @param year         The release year of the movie.
     * @param available    The availability status of the movie.
     * @param rentalPrice  The rental price of the movie.
     * @param video        The video associated with the movie.
     * @throws IllegalArgumentException If the provided year or rental price is negative.
     */
    public Movie(String id, String title, String description, String smallImage, String bigImage, String director, List<Genre> genre, int year, boolean available, double rentalPrice, String video) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.smallImage = smallImage;
        this.bigImage = bigImage;
        this.director = director;
        this.genre = genre;
        if (year < 0) {
            throw new IllegalArgumentException("Year cannot be negative");
        }
        this.year = year;
        this.available = available;
        if (rentalPrice < 0) {
            throw new IllegalArgumentException("Rental price cannot be negative");
        }
        this.rentalPrice = rentalPrice;
        this.video = video;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public String getBigImage() {
        return bigImage;
    }

    public void setBigImage(String bigImage) {
        this.bigImage = bigImage;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public List<Genre> getGenre() {
        return genre;
    }

    public void setGenre(List<Genre> genre) {
        this.genre = genre;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
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
