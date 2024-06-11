package ba.edu.ibu.frent.core.service;

import ba.edu.ibu.frent.rest.websockets.MainSocketHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Service for handling notifications and sending messages through WebSocket.
 */
@Service
public class NotificationService {
    private final MainSocketHandler mainSocketHandler;

    /**
     * Constructs a NotificationService with the provided MainSocketHandler.
     *
     * @param mainSocketHandler The MainSocketHandler for handling WebSocket connections.
     */
    public NotificationService(MainSocketHandler mainSocketHandler) {
        this.mainSocketHandler = mainSocketHandler;
    }

    /**
     * Broadcasts a message to all connected WebSocket sessions.
     *
     * @param message The message to broadcast.
     * @throws IOException If an I/O error occurs while broadcasting the message.
     */
    public void broadcastMessage(String message) throws IOException {
        mainSocketHandler.broadcastMessage(message);
    }

    /**
     * Sends a message to a specific user through WebSocket.
     *
     * @param userName The username of the recipient.
     * @param message  The message to send.
     */
    public void sendMessage(String userName, String message) {
        mainSocketHandler.sendMessage(userName, message);
    }
}

