package ba.edu.ibu.frent.rest.controllers;

import ba.edu.ibu.frent.core.service.AuthService;
import ba.edu.ibu.frent.rest.dto.LoginDTO;
import ba.edu.ibu.frent.rest.dto.LoginRequestDTO;
import ba.edu.ibu.frent.rest.dto.UserDTO;
import ba.edu.ibu.frent.rest.dto.UserRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling user authentication.
 */
@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final AuthService authService;

    /**
     * Constructs an AuthController with the provided AuthService.
     *
     * @param authService The AuthService for handling user authentication.
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Registers a new user.
     *
     * @param user The UserRequestDTO containing user registration information.
     * @return ResponseEntity with the registered user's details.
     */
    @RequestMapping(method = RequestMethod.POST, path = "/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserRequestDTO user) {
        return ResponseEntity.ok(authService.signUp(user));
    }

    /**
     * Logs in a user.
     *
     * @param loginRequest The LoginRequestDTO containing user login credentials.
     * @return ResponseEntity with the generated JWT token.
     */
    @RequestMapping(method = RequestMethod.POST, path = "/login")
    public ResponseEntity<LoginDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        return ResponseEntity.ok(authService.signIn(loginRequest));
    }
}

