package ba.edu.ibu.frent.api.impl.mailsender;

import ba.edu.ibu.frent.core.api.mailsender.MailSender;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Implementation of the {@link MailSender} interface that utilizes the Mailgun service for sending emails.
 * This class is responsible for formatting and sending emails to a list of specified email addresses.
 *
 * @author Edin Bajrić
 * @see MailSender
 */
@Component
public class MailgunSender implements MailSender {

    private final RestTemplate restTemplate;
    private final String fromEmail;

    /**
     * Constructs a new instance of the {@code MailgunSender} class.
     *
     * @param restTemplate The {@link RestTemplate} used for making HTTP requests.
     * @param fromEmail    The sender's email address.
     */
    public MailgunSender(RestTemplate restTemplate, String fromEmail) {
        this.restTemplate = restTemplate;
        this.fromEmail = fromEmail;
    }

    /**
     * Sends an email to the specified list of email addresses using the Mailgun service.
     *
     * @param emails  A list of email addresses to send the email to.
     * @param message The content of the email.
     * @param subject The subject of the email.
     * @return A response string from the Mailgun service.
     */
    @Override
    public String send(List<String> emails, String message, String subject) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("from", fromEmail);

        for (String email : emails) {
            String emailAddress = extractEmailAddressFromJson(email);
            map.add("to", emailAddress);
        }

        map.add("subject", subject);
        map.add("text", message);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        return restTemplate.postForEntity("/messages", request, String.class).getBody();
    }

    /**
     * Extracts the email address from a JSON string representing a user.
     *
     * @param userJson The JSON string representing a user.
     * @return The extracted email address, or an empty string if the extraction fails.
     */
    private String extractEmailAddressFromJson(String userJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(userJson);
            return jsonNode.get("email").asText();
        } catch (Exception e) {
            return "";
        }
    }
}