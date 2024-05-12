package ba.edu.ibu.frent.core.api.mailsender;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * An interface representing a mail sender component. Implementations of this interface provide
 * functionality to send emails to a list of recipients with a specified message and subject.
 */
@Component
public interface MailSender {

    /**
     * Sends an email to the specified list of recipients with the given message and subject.
     *
     * @param emails  The list of email addresses to which the email will be sent.
     * @param message The content of the email message.
     * @param subject The subject of the email.
     * @return A string indicating the result of the email sending process.
     */
    String send(List<String> emails, String message, String subject);
}

