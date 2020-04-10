package com.gs_data_collector.rest.webservices.restfulwebservices;

import com.gs_data_collector.rest.webservices.restfulwebservices.web_scraping.WebScrapeJpaResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class SchedulingTasks {

//    @Scheduled(cron = "0 00 00 * * *")
    @Scheduled(fixedRate = 30000, initialDelay = 60000)
    public void scheduledRun() throws IOException, ParseException {
        System.out.println("Hello world!");
        WebScrapeJpaResource test = new WebScrapeJpaResource();
        test.helloWorld();
    }
}