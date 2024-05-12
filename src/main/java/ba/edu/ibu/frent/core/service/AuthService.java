package ba.edu.ibu.frent.core.service;

import ba.edu.ibu.frent.core.exceptions.auth.UserAlreadyExistsException;
import ba.edu.ibu.frent.core.exceptions.repository.ResourceNotFoundException;
import ba.edu.ibu.frent.core.model.User;
import ba.edu.ibu.frent.core.repository.UserRepository;
import ba.edu.ibu.frent.rest.dto.LoginDTO;
import ba.edu.ibu.frent.rest.dto.LoginRequestDTO;
import ba.edu.ibu.frent.rest.dto.UserDTO;
import ba.edu.ibu.frent.rest.dto.UserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * AuthService provides authentication-related functionality, such as user registration and sign-in.
 */
@Service
public class AuthService {
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Constructs an instance of AuthService with the specified UserRepository.
     *
     * @param userRepository The UserRepository used for user-related database operations.
     */
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Registers a new user based on the provided UserRequestDTO.
     *
     * @param userRequestDTO The UserRequestDTO containing user registration information.
     * @return The UserDTO representing the registered user.
     * @throws UserAlreadyExistsException if the user with the given email or username already exists.
     */
    public UserDTO signUp(UserRequestDTO userRequestDTO) {
        userRequestDTO.setPassword(
                passwordEncoder.encode(userRequestDTO.getPassword())
        );

        if (userRepository.existsByEmail(userRequestDTO.getEmail()) || userRepository.existsByUsername(userRequestDTO.getUsername())) {
            throw new UserAlreadyExistsException("User already exists");
        }
        User user = userRepository.save(userRequestDTO.toEntity());

        return new UserDTO(user);
    }

    /**
     * Authenticates a user based on the provided LoginRequestDTO and generates a JWT token.
     *
     * @param loginRequestDTO The LoginRequestDTO containing user login information.
     * @return The LoginDTO containing the generated JWT token.
     * @throws ResourceNotFoundException if the user with the provided email does not exist.
     */
    public LoginDTO signIn(LoginRequestDTO loginRequestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));
        User user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("This user does not exist."));
        String jwt = jwtService.generateToken(user);

        return new LoginDTO(jwt);
    }
}

