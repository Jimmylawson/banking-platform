package com.jimmyproject.notificationservice.web;

import com.jimmyproject.notificationservice.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {
    private final EmailService emailService;

    @GetMapping("/check-email")
    public String checkEmail() {
        return "Current sender email: " + emailService.getSenderEmail();
    }
}