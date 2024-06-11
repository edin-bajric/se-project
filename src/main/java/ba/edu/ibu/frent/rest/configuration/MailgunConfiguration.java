package ba.edu.ibu.frent.rest.configuration;

import ba.edu.ibu.frent.api.impl.mailsender.MailgunSender;
import ba.edu.ibu.frent.core.api.mailsender.MailSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

/**
 * Configuration class for setting up Mailgun email sending functionality.
 */
@Configuration
public class MailgunConfiguration {

    /**
     * Value for the 'from' email address used for sending emails.
     */
    @Value("${email.mailgun.from-email}")
    private String fromEmail;

    /**
     * Value for the Mailgun account username.
     */
    @Value("${email.mailgun.username}")
    private String username;

    /**
     * Value for the Mailgun account password.
     */
    @Value("${email.mailgun.password}")
    private String password;

    /**
     * Value for the Mailgun domain.
     */
    @Value("${email.mailgun.domain}")
    private String domain;

    /**
     * Bean definition for the 'from' email address.
     *
     * @return The 'from' email address.
     */
    @Bean
    public String fromEmail() {
        return fromEmail;
    }

    /**
     * Bean definition for creating a MailSender instance with Mailgun configuration.
     *
     * @param restTemplate The RestTemplate for HTTP communication.
     * @param fromEmail    The 'from' email address for sending emails.
     * @return MailSender instance configured for Mailgun.
     */
    @Bean
    public MailSender mailgunMailSender(RestTemplate restTemplate, String fromEmail) {
        return new MailgunSender(restTemplate, fromEmail);
    }

    /**
     * Bean definition for creating a RestTemplate with Mailgun authentication.
     *
     * @param builder The RestTemplateBuilder for building the RestTemplate.
     * @return RestTemplate configured for Mailgun authentication.
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.uriTemplateHandler(new DefaultUriBuilderFactory(domain))
                .basicAuthentication(username, password)
                .build();
    }
}

