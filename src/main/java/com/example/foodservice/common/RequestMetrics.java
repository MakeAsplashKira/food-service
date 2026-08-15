package com.example.foodservice.common;


import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class RequestMetrics {
    private final ThreadLocal<Data> threadLocal = new ThreadLocal<>();

    public void start() {
        Data data = new Data();
        data.startTime = System.currentTimeMillis();
        threadLocal.set(data);
    }

    public Stats finishAndGetStats() {
        Data data = threadLocal.get();
        threadLocal.remove();
        if(data == null) return null;

        long duration = System.currentTimeMillis() - data.startTime;

        return new Stats(
          duration,
          LocalDateTime.now()
        );
    }

    private static class Data {
        long startTime;
    }

    public record Stats(
            long durationMs,
            LocalDateTime timeStamp
    ) {}
}
