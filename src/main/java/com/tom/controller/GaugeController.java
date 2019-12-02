package com.tom.controller;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Gauge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GaugeController {
    private final Gauge queueSize;

    public GaugeController(CollectorRegistry collectorRegistry) {
        queueSize = Gauge.build()
                .name("queue_size")
                .help("Size of queue.")
                .register(collectorRegistry);
    }

    @GetMapping(value = "/push")
    public String push() {
        queueSize.inc();

        return "You pushed an item to the queue!";
    }

    @GetMapping(value = "/pop")
    public String pop() {
        queueSize.dec();

        return "You popped an item from the queue!";
    }
}
