package ba.edu.ibu.frent.rest.controllers;

import ba.edu.ibu.frent.core.service.UserService;
import ba.edu.ibu.frent.rest.dto.UserDTO;
import ba.edu.ibu.frent.rest.dto.UserRequestDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;

/**
 * Controller for managing user-related operations.
 */
@RestController
@RequestMapping("api/users")
@SecurityRequirement(name = "JWT Security")
public class UserController {
    private final UserService userService;

    /**
     * Constructor for UserController.
     *
     * @param userService The UserService instance.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get a list of all users.
     *
     * @return ResponseEntity containing the list of UserDTOs.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    /**
     * Register a new user.
     *
     * @param user The UserRequestDTO containing user details.
     * @return ResponseEntity containing the registered UserDTO.
     */
    @RequestMapping(method = RequestMethod.POST, path = "/register")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> register(@RequestBody UserRequestDTO user) {
        return ResponseEntity.ok(userService.addUser(user));
    }

    /**
     * Get user details by user ID.
     *
     * @param id The ID of the user.
     * @return ResponseEntity containing the UserDTO with the specified ID.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    /**
     * Update user details.
     *
     * @param id   The ID of the user to be updated.
     * @param user The UserRequestDTO containing updated user details.
     * @return ResponseEntity containing the updated UserDTO.
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String id, @RequestBody UserRequestDTO user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    /**
     * Delete a user by user ID.
     *
     * @param id The ID of the user to be deleted.
     * @return ResponseEntity with HttpStatus.NO_CONTENT if the deletion is successful.
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Filter user by email address.
     *
     * @param email The email address for filtering users.
     * @return ResponseEntity containing the filtered UserDTO.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/filter")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<UserDTO> filterUser(@RequestParam String email) {
        return ResponseEntity.ok(userService.filterByEmail(email));
    }

    /**
     * Add a movie to the user's cart.
     *
     * @param movieId   The ID of the movie to be added to the cart.
     * @param principal The Principal representing the current user.
     * @return ResponseEntity containing the updated UserDTO with the added movie to the cart.
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/addToCart/{movieId}")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'ADMIN')")
    public ResponseEntity<UserDTO> addToCart(@PathVariable String movieId, Principal principal) {
        String username = principal.getName();
        UserDTO updatedUser = userService.addToCart(movieId, username);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Add a movie to the user's wishlist.
     *
     * @param movieId   The ID of the movie to be added to the wishlist.
     * @param principal The Principal representing the current user.
     * @return ResponseEntity containing the updated UserDTO with the added movie to the wishlist.
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/addToWishlist/{movieId}")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'ADMIN')")
    public ResponseEntity<UserDTO> addToWishlist(@PathVariable String movieId, Principal principal) {
        String username = principal.getName();
        UserDTO updatedUser = userService.addToWishlist(movieId, username);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Remove a movie from the user's cart.
     *
     * @param movieId   The ID of the movie to be removed from the cart.
     * @param principal The Principal representing the current user.
     * @return ResponseEntity containing the updated UserDTO with the removed movie from the cart.
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/removeFromCart/{movieId}")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'ADMIN')")
    public ResponseEntity<UserDTO> removeFromCart(@PathVariable String movieId, Principal principal) {
        String username = principal.getName();
        UserDTO updatedUser = userService.removeFromCart(movieId, username);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Remove a movie from the user's wishlist.
     *
     * @param movieId   The ID of the movie to be removed from the wishlist.
     * @param principal The Principal representing the current user.
     * @return ResponseEntity containing the updated UserDTO with the removed movie from the wishlist.
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/removeFromWishlist/{movieId}")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'ADMIN')")
    public ResponseEntity<UserDTO> removeFromWishlist(@PathVariable String movieId, Principal principal) {
        String username = principal.getName();
        UserDTO updatedUser = userService.removeFromWishlist(movieId, username);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Get the user's cart.
     *
     * @param principal The Principal representing the current user.
     * @return ResponseEntity containing the set of movie IDs in the user's cart.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/cart")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'ADMIN')")
    public ResponseEntity<Set<String>> getCart(Principal principal) {
        String username = principal.getName();
        Set<String> cart = userService.getCart(username);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    /**
     * Get the user's wishlist.
     *
     * @param principal The Principal representing the current user.
     * @return ResponseEntity containing the set of movie IDs in the user's wishlist.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/wishlist")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'ADMIN')")
    public ResponseEntity<Set<String>> getWishlist(Principal principal) {
        String username = principal.getName();
        Set<String> wishlist = userService.getWishlist(username);
        return new ResponseEntity<>(wishlist, HttpStatus.OK);
    }

    /**
     * Get the total price of the user's cart.
     *
     * @param principal The Principal representing the current user.
     * @return ResponseEntity containing the total price of the movies in the user's cart.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/cartTotal")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'ADMIN')")
    public ResponseEntity<Double> getCartTotal(Principal principal) {
        String username = principal.getName();
        double cartTotal = userService.getCartTotal(username);
        return ResponseEntity.ok(cartTotal);
    }
}
