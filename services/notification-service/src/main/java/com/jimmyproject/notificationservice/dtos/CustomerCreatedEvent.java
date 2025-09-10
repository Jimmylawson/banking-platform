package com.jimmyproject.notificationservice.dtos;

import java.time.Instant;

public record CustomerCreatedEvent(
        String eventType,
        Long customerId,
        String email,

        Instant occurredAt
) {}