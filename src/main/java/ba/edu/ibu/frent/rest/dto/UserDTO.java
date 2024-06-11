package ba.edu.ibu.frent.rest.dto;

import ba.edu.ibu.frent.core.model.enums.UserType;
import ba.edu.ibu.frent.core.model.User;

import java.util.Date;
import java.util.Set;

/**
 * UserDTO represents the data transfer object for presenting User entities.
 */
public class UserDTO {
    private String id;
    private String name;
    private String username;
    private UserType userType;
    private String email;
    private Set<String> cart;
    private Set<String> wishlist;
    private Date creationDate;
    private boolean isSuspended;

    /**
     * Constructs an empty UserDTO.
     */
    public UserDTO() {
    }

    /**
     * Constructs a UserDTO based on the provided User entity.
     *
     * @param user The User entity from which to construct the DTO.
     */
    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getFirstName() + " " + user.getLastName();
        this.username = user.getUsername();
        this.userType = user.getUserType();
        this.email = user.getEmail();
        this.cart = user.getCart();
        this.wishlist = user.getWishlist();
        this.creationDate = user.getCreationDate();
        this.isSuspended = user.getIsSuspended();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getCart() {
        return cart;
    }

    public void setCart(Set<String> cart) {
        this.cart = cart;
    }

    public Set<String> getWishlist() {
        return wishlist;
    }

    public void setWishlist(Set<String> wishlist) {
        this.wishlist = wishlist;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean getIsSuspended() {
        return isSuspended;
    }

    public void setSuspended(boolean isSuspended) {
        this.isSuspended = isSuspended;
    }
}
