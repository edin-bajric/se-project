package ba.edu.ibu.frent.rest.dto;

import ba.edu.ibu.frent.core.model.Movie;
import ba.edu.ibu.frent.core.model.enums.Genre;

import java.util.List;

/**
 * MovieRequestDTO represents the data transfer object for creating or updating movies.
 */
public class MovieRequestDTO {
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
     * Constructs an empty MovieRequestDTO.
     */
    public MovieRequestDTO() {
    }

    /**
     * Constructs a MovieRequestDTO based on the provided Movie entity.
     *
     * @param movie The Movie entity from which to construct the DTO.
     */
    public MovieRequestDTO(Movie movie) {
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

    /**
     * Converts the DTO to a Movie entity.
     *
     * @return The Movie entity created from the DTO.
     */
    public Movie toEntity() {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setDescription(description);
        movie.setSmallImage(smallImage);
        movie.setBigImage(bigImage);
        movie.setDirector(director);
        movie.setGenre(genre);
        movie.setYear(year);
        movie.setAvailable(available);
        movie.setRentalPrice(rentalPrice);
        movie.setVideo(video);
        return movie;
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
