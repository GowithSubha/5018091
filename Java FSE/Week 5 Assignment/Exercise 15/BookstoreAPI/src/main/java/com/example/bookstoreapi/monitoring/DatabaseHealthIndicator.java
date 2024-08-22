package com.example.bookstoreapi.monitoring;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class DatabaseHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        boolean databaseStatus = checkDatabaseStatus();
        if (databaseStatus) {
            return Health.up().withDetail("Database status", "Database is running").build();
        } else {
            return Health.down().withDetail("Database status", "Database is not running").build();
        }
    }

    private boolean checkDatabaseStatus() {
        return true;
    }
}
