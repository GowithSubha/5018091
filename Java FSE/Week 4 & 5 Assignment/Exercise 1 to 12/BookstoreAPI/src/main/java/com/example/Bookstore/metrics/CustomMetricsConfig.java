package com.example.Bookstore.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomMetricsConfig {

    private final Counter bookCounter;
    private final Timer bookCreationTimer;

    public CustomMetricsConfig(MeterRegistry meterRegistry) {
        this.bookCounter = meterRegistry.counter("bookstore.books.count");
        this.bookCreationTimer = meterRegistry.timer("bookstore.books.creation.timer");
    }

    public void incrementBookCounter() {
        bookCounter.increment();
    }

    public Timer.Sample startBookCreationTimer(MeterRegistry meterRegistry) {
        return Timer.start(meterRegistry);
    }

    public void stopBookCreationTimer(Timer.Sample sample) {
        sample.stop(bookCreationTimer);
    }
}
