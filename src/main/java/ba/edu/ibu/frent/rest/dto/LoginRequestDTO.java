package ba.edu.ibu.frent.rest.dto;

/**
 * LoginRequestDTO represents the data transfer object for user login requests.
 */
public class LoginRequestDTO {
    private String email;
    private String password;

    /**
     * Constructs a LoginRequestDTO with the provided email and password.
     *
     * @param email    The user's email for authentication.
     * @param password The user's password for authentication.
     */
    public LoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
