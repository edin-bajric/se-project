package ba.edu.ibu.frent.rest.controllers;

import ba.edu.ibu.frent.core.service.RentalService;
import ba.edu.ibu.frent.rest.dto.RentalDTO;
import ba.edu.ibu.frent.rest.dto.RentalRequestDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Controller class for managing rental operations.
 */
@RestController
@RequestMapping("api/rentals")
@SecurityRequirement(name = "JWT Security")
public class RentalController {

    private final RentalService rentalService;

    /**
     * Constructs a new RentalController with the specified RentalService.
     *
     * @param rentalService The service responsible for rental-related operations.
     */
    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    /**
     * Retrieves a list of all rentals.
     *
     * @return ResponseEntity containing a list of RentalDTOs and HTTP status OK.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<List<RentalDTO>> getRentals() {
        return ResponseEntity.ok(rentalService.getRentals());
    }

    /**
     * Adds a new rental.
     *
     * @param rental The RentalRequestDTO containing information for the new rental.
     * @return ResponseEntity containing the created RentalDTO and HTTP status OK.
     */
    @RequestMapping(method = RequestMethod.POST, path = "/add")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<RentalDTO> add(@RequestBody RentalRequestDTO rental) {
        return ResponseEntity.ok(rentalService.addRental(rental));
    }

    /**
     * Retrieves a rental by its ID.
     *
     * @param id The ID of the rental to retrieve.
     * @return ResponseEntity containing the requested RentalDTO and HTTP status OK.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<RentalDTO> getRentalById(@PathVariable String id) {
        return ResponseEntity.ok(rentalService.getRentalById(id));
    }

    /**
     * Updates an existing rental by its ID.
     *
     * @param id      The ID of the rental to update.
     * @param rental  The RentalRequestDTO containing updated information for the rental.
     * @return ResponseEntity containing the updated RentalDTO and HTTP status OK.
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<RentalDTO> updateRental(@PathVariable String id, @RequestBody RentalRequestDTO rental) {
        return ResponseEntity.ok(rentalService.updateRental(id, rental));
    }

    /**
     * Deletes a rental by its ID.
     *
     * @param id The ID of the rental to delete.
     * @return ResponseEntity with HTTP status NO_CONTENT.
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<Void> deleteRental(@PathVariable String id) {
        rentalService.deleteRental(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Marks a rental as returned.
     *
     * @param id The ID of the rental to be returned.
     * @return ResponseEntity containing the updated RentalDTO.
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/return/{id}")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'EMPLOYEE', 'ADMIN')")
    public ResponseEntity<RentalDTO> returnRental(@PathVariable String id) {
        RentalDTO updatedRental = rentalService.returnRental(id);
        return ResponseEntity.ok(updatedRental);
    }

    /**
     * Retrieves rentals associated with the currently authenticated user.
     *
     * @param principal The authenticated user's Principal object.
     * @return ResponseEntity containing a list of RentalDTOs.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/getForUser")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'EMPLOYEE', 'ADMIN')")
    public ResponseEntity<List<RentalDTO>> getRentalsForUser(Principal principal) {
        String username = principal.getName();
        List<RentalDTO> rentalIds = rentalService.getRentalsForUser(username);
        return ResponseEntity.ok(rentalIds);
    }

    /**
     * Adds a rental for the currently authenticated user.
     *
     * @param movieId   The ID of the movie to be rented.
     * @param rental    The RentalRequestDTO containing rental details.
     * @param principal The authenticated user's Principal object.
     * @return ResponseEntity containing the added RentalDTO.
     */
    @RequestMapping(method = RequestMethod.POST, path = "/addForUser/{movieId}")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'EMPLOYEE', 'ADMIN')")
    public ResponseEntity<RentalDTO> addForUser(@PathVariable String movieId, @RequestBody RentalRequestDTO rental, Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok(rentalService.addRentalForUser(username, movieId, rental));
    }

    /**
     * Retrieves a rental for the currently authenticated user by ID.
     *
     * @param id        The ID of the rental to be retrieved.
     * @param principal The authenticated user's Principal object.
     * @return ResponseEntity containing the retrieved RentalDTO.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/getForUser/{id}")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'EMPLOYEE', 'ADMIN')")
    public ResponseEntity<RentalDTO> getRentalByIdForUser(@PathVariable String id, Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok(rentalService.getRentalByIdForUser(id, username));
    }

    /**
     * Deletes a rental for the currently authenticated user by ID.
     *
     * @param id        The ID of the rental to be deleted.
     * @param principal The authenticated user's Principal object.
     * @return ResponseEntity indicating the success of the operation.
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/deleteForUser/{id}")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'EMPLOYEE', 'ADMIN')")
    public ResponseEntity<Void> deleteRentalForUser(@PathVariable String id, Principal principal) {
        String username = principal.getName();
        rentalService.deleteRentalForUser(id, username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Sends due date warnings for rentals with approaching due dates.
     *
     * @return ResponseEntity indicating the success of the operation.
     */
    @RequestMapping(method = RequestMethod.POST, path = "/sendDueDateWarnings")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<Void> sendDueDateWarnings() {
        rentalService.checkDueDatesAndSendWarnings();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Retrieves the total amount spent on rentals by the currently authenticated user.
     *
     * @param principal The authenticated user's Principal object.
     * @return ResponseEntity containing the total amount spent.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/getTotalSpent")
    @PreAuthorize("hasAnyAuthority('MEMBER', 'EMPLOYEE', 'ADMIN')")
    public ResponseEntity<Double> getTotalSpent(Principal principal) {
        String username = principal.getName();
        Double totalSpent = rentalService.getTotalSpentOnRentals(username);
        return ResponseEntity.ok(totalSpent);
    }

    /**
     * Retrieves the total amount spent on rentals by a user with the specified ID.
     *
     * @param id The ID of the user to retrieve the total amount spent for.
     * @return ResponseEntity containing the total amount spent.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/getTotalSpentByUser/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Double> getTotalSpentByUser(@PathVariable String id) {
        Double totalSpent = rentalService.getTotalSpentOnRentalsById(id);
        return ResponseEntity.ok(totalSpent);
    }

    /**
     * Retrieves rentals associated with a user with the specified ID.
     *
     * @param id The ID of the user to retrieve rentals for.
     * @return ResponseEntity containing a list of RentalDTOs.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/getAllForUser/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<List<RentalDTO>> getRentalsForUser(@PathVariable String id) {
        List<RentalDTO> rentals = rentalService.getRentalsForUserByUserId(id);
        return ResponseEntity.ok(rentals);
    }
}
