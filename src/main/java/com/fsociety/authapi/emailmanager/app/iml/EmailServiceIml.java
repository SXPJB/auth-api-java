package com.fsociety.authapi.emailmanager.app.iml;

import com.fsociety.authapi.emailmanager.app.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceIml implements EmailService {

    private final Logger log = LoggerFactory.getLogger(EmailServiceIml.class);
    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Value("${config.domain.name}")
    private String domainName;

    public EmailServiceIml(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }


    @Override
    public void sendConfirmationEmail(String username, String email, String confirmationCode) {
        new Thread(() -> {
            log.info("*** Sending confirmation email from {} to {}", from, email);
            log.info("*** Confirmation code: {}", confirmationCode);
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            try {
                String confirmationLink = domainName + "/api/v1/confirm-user/" + username + "/" + confirmationCode;
                // TODO: Move this to a template
                String htmlMsg = """
                                <html lang="en">
                                  <head>
                                    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
                                          rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
                                          crossorigin="anonymous">
                                  </head>
                                  <body>
                                    <div class="container mt-3">
                                      <div class="row">
                                        <div class="col-12">
                                            <h1>Hello ${username}</h1>
                                            <p>Thank you for registering with us. Please click the link below to confirm your email address.</p>
                                            <a href="${confirmationLink}">Confirm Email</a>
                                        </div>
                                      </div>
                                  </body>
                                </html>
                        """.replace("${username}", email)
                        .replace("${confirmationLink}", confirmationLink);

                helper.setFrom(from);
                helper.setTo(email);
                helper.setSubject("Confirm your email");
                helper.setText(htmlMsg, true);

                emailSender.send(message);
            } catch (MessagingException e) {
                log.error("Error sending confirmation email to {}", email, e);
            }
        }).start();
    }


}
