package com.jimmyproject.notificationservice.consumer;

import com.jimmyproject.notificationservice.dtos.CustomerCreatedEvent;
import com.jimmyproject.notificationservice.services.EmailService;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationConsumer {
    private final EmailService emailService;


    public Consumer<CustomerCreatedEvent> handleCustomerCreated(){
        return event -> {
            log.info("Received customer created event for: {}", event.email());

            try {
                // Extract the first name from the email (everything before @)
                String firstName = event.email().split("@")[0];
                
                emailService.sendWelcomeEmail(
                        event.email(),
                        firstName
                );
                
                log.info("Successfully sent welcome email to: {}", event.email());
            } catch (Exception e) {
                log.error("Error processing customer created event for email: {}", event.email(), e);
            }
        };
    }
}
