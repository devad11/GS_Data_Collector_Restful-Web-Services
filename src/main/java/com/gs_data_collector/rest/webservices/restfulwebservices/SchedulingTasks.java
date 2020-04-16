package com.gs_data_collector.rest.webservices.restfulwebservices;

import com.gs_data_collector.rest.webservices.restfulwebservices.web_scraping.WebJpaRepository;
import com.gs_data_collector.rest.webservices.restfulwebservices.web_scraping.WebScrapeJpaResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class SchedulingTasks {

    @Autowired
    private WebScrapeJpaResource webScrapeJpaResource;
    ArrayList<String> info = new ArrayList<String>();
    private final TaskScheduler executor;

    @Autowired
    public SchedulingTasks(TaskScheduler taskExecutor) {
        this.executor = taskExecutor;
    }

    public void scheduling(final Runnable task) throws IOException, ParseException {

        System.out.println("We are in scheduling");
        // Schedule a task that will first run at the given date and every 1000ms
        executor.scheduleAtFixedRate(webScrapeJpaResource, Date.from(LocalDateTime.now().plusMinutes(1)
                .atZone(ZoneId.systemDefault()).toInstant()), 15000);

        // Schedule a task with the given cron expression
//        executor.schedule(task, new CronTrigger("*/5 * * * * MON-FRI"));
    }

//    @Scheduled(cron = "0 00 00 * * *")
    @Scheduled(fixedRate = 30000, initialDelay = 30000)
    public void scheduledRun() throws IOException, ParseException {
        System.out.println("We are in scheduledRun");
        scheduling(null);
    }

}