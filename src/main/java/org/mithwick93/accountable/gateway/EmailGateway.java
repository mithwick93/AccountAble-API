package org.mithwick93.accountable.gateway;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.emails.Email;
import com.mailersend.sdk.exceptions.MailerSendException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mithwick93.accountable.gateway.dto.request.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class EmailGateway {

    @Value("${email.mailersend.api-token}")
    private String apiToken;

    @Value("${email.sender.name}")
    private String senderName;

    @Value("${email.sender.email}")
    private String senderEmail;

    public void sendEmail(EmailRequest emailRequest) {
        Email email = new Email();

        email.setFrom(senderName, senderEmail);
        email.addRecipient(emailRequest.recipientName(), emailRequest.recipientEmail());

        email.setSubject(emailRequest.subject());

        if (emailRequest.textContent() != null) {
            email.setPlain(emailRequest.textContent());
        }

        if (emailRequest.htmlContent() != null) {
            email.setHtml(emailRequest.htmlContent());
        }

        MailerSend ms = new MailerSend();
        ms.setToken(apiToken);

        try {
            MailerSendResponse response = ms.emails().send(email);
            log.info("Email sent, message ID: {}", response.messageId);
        } catch (MailerSendException e) {
            log.error("Failed to send email: {}", e.getMessage());
            throw new RuntimeException("Failed to send email", e);
        }
    }

}
