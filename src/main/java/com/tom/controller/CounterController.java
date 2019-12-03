package com.tom.controller;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CounterController {
    private final Counter requestCount;

    public CounterController(CollectorRegistry collectorRegistry) {
        requestCount = Counter.build()
                .name("request_count")
                .help("Number of hello requests.")
                .register(collectorRegistry);
    }

    @GetMapping(value = "/hello")
    public String hello() {
        requestCount.inc();

        return "Hi!";
    }
}
