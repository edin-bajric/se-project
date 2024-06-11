package ba.edu.ibu.frent.rest.dto;

/**
 * MessageDTO represents the data transfer object for messages.
 */
public class MessageDTO {
    private String message;

    /**
     * Constructs an empty MessageDTO.
     */
    public MessageDTO() {}

    /**
     * Constructs a MessageDTO with the provided message.
     *
     * @param message The content of the message.
     */
    public MessageDTO(String message) {
        this.message = message;
    }

    /**
     * Gets the content of the message.
     *
     * @return The message content.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the content of the message.
     *
     * @param message The new message content.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
