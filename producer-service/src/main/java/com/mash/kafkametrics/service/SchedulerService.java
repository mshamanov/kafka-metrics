package com.mash.kafkametrics.service;

import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.Executors;

/**
 * Interface for executing scheduled tasks either by using appropriate thread pool from {@link Executors} or by
 * using {@link Scheduled} annotation.
 *
 * @author Mikhail Shamanov
 */
public interface SchedulerService {
    void schedule();
}
