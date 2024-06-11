package ba.edu.ibu.frent.rest.controllers;

import ba.edu.ibu.frent.core.service.NotificationService;
import ba.edu.ibu.frent.rest.dto.MessageDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Controller for handling notifications.
 */
@RestController
@RequestMapping(path = "api/notifications")
@SecurityRequirement(name = "JWT Security")
public class NotificationController {
    private final NotificationService notificationService;

    /**
     * Constructs a NotificationController with the provided NotificationService.
     *
     * @param notificationService The NotificationService for handling notifications.
     */
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Sends a broadcast message to all connected users.
     *
     * @param message The message to broadcast.
     * @return ResponseEntity with NO_CONTENT status.
     * @throws IOException If an I/O error occurs while broadcasting the message.
     */
    @RequestMapping(method = RequestMethod.POST, path = "/broadcast")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<Void> sendBroadcastMessage(@RequestBody MessageDTO message) throws IOException {
        System.out.println("The message is: " + message.getMessage());
        notificationService.broadcastMessage(message.getMessage());
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    /**
     * Sends a chat message to a specific user.
     *
     * @param userName The username of the recipient.
     * @param message  The message to send.
     * @return ResponseEntity with NO_CONTENT status.
     * @throws IOException If an I/O error occurs while sending the message.
     */
    @RequestMapping(method = RequestMethod.POST, path = "/send-to/{userName}")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<Void> sendChatMessage(@PathVariable String userName, @RequestBody MessageDTO message) throws IOException {
        System.out.println("The message is: " + message.getMessage());
        notificationService.sendMessage(userName, message.getMessage());
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}

