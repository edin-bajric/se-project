package ba.edu.ibu.frent.rest.dto;

import ba.edu.ibu.frent.core.model.Movie;
import ba.edu.ibu.frent.core.model.enums.Genre;

import java.util.List;

/**
 * MovieDTO represents the data transfer object for movies.
 */
public class MovieDTO {
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
     * Constructs an empty MovieDTO.
     */
    public MovieDTO() {
    }

    /**
     * Constructs a MovieDTO based on the provided Movie entity.
     *
     * @param movie The Movie entity from which to construct the DTO.
     */
    public MovieDTO(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.description = movie.getDescription();
        this.smallImage = movie.getSmallImage();
        this.bigImage = movie.getBigImage();
        this.director = movie.getDirector();
        this.genre = movie.getGenre();
        this.year = movie.getYear();
        this.available = movie.isAvailable();
        this.rentalPrice = movie.getRentalPrice();
        this.video = movie.getVideo();
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
