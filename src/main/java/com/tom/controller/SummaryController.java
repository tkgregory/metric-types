package com.tom.controller;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Summary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.Thread.sleep;

@RestController
public class SummaryController {
    private final Summary requestDuration;

    public SummaryController(CollectorRegistry collectorRegistry) {
        requestDuration = Summary.build()
                .name("request_duration_summary")
                .help("Time for HTTP request.")
                .quantile(0.95, 0.01)
                .register(collectorRegistry);
    }

    @GetMapping(value = "/waitSummary")
    public String makeMeWait() throws InterruptedException {
        Summary.Timer timer = requestDuration.startTimer();

        long sleepDuration = Double.valueOf(Math.floor(Math.random() * 10 * 1000)).longValue();
        sleep(sleepDuration);

        timer.observeDuration();

        return String.format("I kept you waiting for %s ms!", sleepDuration);
    }
}
