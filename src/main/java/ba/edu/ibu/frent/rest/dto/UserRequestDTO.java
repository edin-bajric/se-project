package ba.edu.ibu.frent.rest.dto;

import ba.edu.ibu.frent.core.model.User;
import ba.edu.ibu.frent.core.model.enums.UserType;

import java.util.Date;

import static ba.edu.ibu.frent.core.model.enums.UserType.MEMBER;

/**
 * UserRequestDTO represents the data transfer object for creating or updating User entities.
 */
public class UserRequestDTO {
    private UserType userType;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;

    /**
     * Constructs an empty UserRequestDTO.
     */
    public UserRequestDTO() { }

    /**
     * Constructs a UserRequestDTO based on the provided User entity.
     *
     * @param user The User entity from which to construct the DTO.
     */
    public UserRequestDTO(User user) {
        this.userType = user.getUserType();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.password = user.getPassword();
    }

    /**
     * Converts the UserRequestDTO to a User entity.
     *
     * @return The User entity created from the DTO.
     */
    public User toEntity() {
        User user = new User();
        user.setUserType(MEMBER);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        user.setCreationDate(new Date());
        user.setIsSuspended(false);
        return user;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
