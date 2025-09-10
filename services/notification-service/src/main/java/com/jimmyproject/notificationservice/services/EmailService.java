package com.jimmyproject.notificationservice.services;

import com.jimmyproject.notificationservice.dtos.CustomerCreatedEvent;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;
    @Value("${sendgrid.sender.email}")
    private String senderEmail;

    @PostConstruct
    public void init(){
        log.info("SendGrid API Key configured: {}", sendGridApiKey != null ? "Yes" : "No");
        log.info("SendGrid Sender Email configured: {}", senderEmail != null ? "Yes" : "No");
    }

    public void sendWelcomeEmail(String toEmail, String firstName){
        Email from  = new Email(senderEmail);
        String subject = "Welcome to our Banking platform";
        Email to = new Email(toEmail);
        Content content = new Content("text/plain", String.format(
                """
                        Dear %s,
                        
                                        Welcome to Our Bank! We're excited to have you on board.
                        
                                        Your account has been successfully created.\s
                        
                                        Best regards,
                                        The Banking Team
                                        ""\", firstName));
                        """
        ,firstName));

        Mail mail = new Mail(from, subject, to, content);
        SendGrid sendGrid = new SendGrid(sendGridApiKey);
        Request request = new Request();

        try{
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            var response = sendGrid.api(request);

            if(response.getStatusCode() >= 200 && response.getStatusCode() < 300) log.info("Email sent successfully to {}", toEmail);
            else {
                log.error("Failed to send email to {}, Body: {}", toEmail, response.getBody());
            }

        }catch (Exception e){
            log.error("Error sending email to {}", toEmail,e);
        }
    }

    public String getSenderEmail() {
        return senderEmail;
    }

}
