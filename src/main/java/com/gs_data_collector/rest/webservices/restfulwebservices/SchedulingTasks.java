package com.gs_data_collector.rest.webservices.restfulwebservices;

import com.gs_data_collector.rest.webservices.restfulwebservices.web_scraping.WebJpaRepository;
import com.gs_data_collector.rest.webservices.restfulwebservices.web_scraping.WebScrapeJpaResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class SchedulingTasks {

    @Autowired
    private WebScrapeJpaResource webScrapeJpaResource;
    ArrayList<String> info = new ArrayList<String>();




//    @Scheduled(cron = "0 00 00 * * *")
    @Scheduled(fixedRate = 30000, initialDelay = 60000)
    public void scheduledRun() throws IOException, ParseException {
        System.out.println("Hello world!");
        webScrapeJpaResource.webMining();
    }
}