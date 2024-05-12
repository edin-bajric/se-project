package ba.edu.ibu.frent.rest.dto;

/**
 * LoginDTO represents the data transfer object for JWT authentication responses.
 */
public class LoginDTO {
    private String jwt;

    /**
     * Constructs a LoginDTO with the provided JWT.
     *
     * @param jwt The JWT associated with the authentication response.
     */
    public LoginDTO(String jwt) {
        this.jwt = jwt;
    }

    /**
     * Gets the JWT from the authentication response.
     *
     * @return The JWT.
     */
    public String getJwt() {
        return jwt;
    }

    /**
     * Sets the JWT in the authentication response.
     *
     * @param jwt The JWT to set.
     */
    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}

