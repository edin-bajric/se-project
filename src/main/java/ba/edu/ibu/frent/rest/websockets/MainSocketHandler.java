package ba.edu.ibu.frent.rest.websockets;

import ba.edu.ibu.frent.core.exceptions.GeneralException;
import ba.edu.ibu.frent.core.model.User;
import ba.edu.ibu.frent.core.service.JwtService;
import ba.edu.ibu.frent.core.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * MainSocketHandler is a WebSocket handler responsible for managing WebSocket sessions and handling
 * incoming WebSocket messages.
 */
@Component
public class MainSocketHandler implements WebSocketHandler {
    private final JwtService jwtService;
    private final UserService userService;
    public Map<String, WebSocketSession> sessions = new HashMap<>();

    /**
     * Constructs a MainSocketHandler with the required dependencies.
     *
     * @param jwtService The JWT service for token extraction.
     * @param userService The user service for user-related operations.
     */
    public MainSocketHandler(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    /**
     * Invoked after a new WebSocket connection is established.
     *
     * @param session The WebSocket session.
     * @throws Exception If an error occurs during connection establishment.
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        User user = getUser(session);
        if (user == null) {
            return;
        }
        sessions.put(user.getUsername(), session);
        System.out.println("Session created for the user " + user.getUsername() +
                " where the session id is " + session.getId());
    }

    /**
     * Invoked when a new WebSocket message is received.
     *
     * @param session The WebSocket session.
     * @param message The received WebSocket message.
     * @throws Exception If an error occurs while handling the message.
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String messageReceived = (String) message.getPayload();
        System.out.println("Message received: " + messageReceived);
    }

    /**
     * Invoked when a transport error occurs.
     *
     * @param session   The WebSocket session.
     * @param exception The exception representing the error.
     * @throws Exception If an error occurs while handling the transport error.
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("Error happened " + session.getId() +
                " with reason ### " + exception.getMessage());
    }

    /**
     * Invoked after a WebSocket connection is closed.
     *
     * @param session     The WebSocket session.
     * @param closeStatus The close status indicating the reason for closure.
     * @throws Exception If an error occurs during connection closure.
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("Connection closed for session " + session.getId() +
                " with status ### " + closeStatus.getReason());
    }

    /**
     * Indicates whether this WebSocketHandler handles partial messages.
     *
     * @return Always returns false.
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * Retrieves the user associated with the WebSocket session.
     *
     * @param session The WebSocket session.
     * @return The user associated with the session, or null if no user is found.
     * @throws IOException If an I/O error occurs.
     */
    private User getUser(WebSocketSession session) throws IOException {
        List<String> headers = session.getHandshakeHeaders().getOrEmpty("authorization");
        if (headers.isEmpty()) {
            session.close();
            return null;
        }

        String jwt = headers.get(0).substring(7);
        String userName = jwtService.extractUserName(jwt);

        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userName);
        return (User) userDetails;
    }

    /**
     * Broadcasts a message to all connected WebSocket sessions.
     *
     * @param message The message to broadcast.
     * @throws IOException If an I/O error occurs while sending the message.
     */
    public void broadcastMessage(String message) throws IOException {
        sessions.forEach((key, session) -> {
            try {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(message));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Sends a message to a specific user's WebSocket session.
     *
     * @param userName The username of the user to whom the message should be sent.
     * @param message  The message to send.
     */
    public void sendMessage(String userName, String message) {
        WebSocketSession session = sessions.get(userName);
        if (session == null) {
            return;
        }
        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            throw new GeneralException(e);
        }
    }
}

