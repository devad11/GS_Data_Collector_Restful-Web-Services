package com.gs_data_collector.rest.webservices.restfulwebservices.scheduling;

import com.gs_data_collector.rest.webservices.restfulwebservices.api_source.ApiDataResource;
import com.gs_data_collector.rest.webservices.restfulwebservices.web_scraping.WebScrapeJpaResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

@Component
@Slf4j
public class SchedulingTasks {

    @Autowired
    private SchedulingJpaResource schedulingJpaResource;
    @Autowired
    private WebScrapeJpaResource webScrapeJpaResource;
    @Autowired
    private ApiDataResource apiDataResource;
    ArrayList<String> info = new ArrayList<String>();
    private final TaskScheduler executor;

    List<Data_collector> listWebScrapeTasks;
    List<Data_collector> listApiTasks;

    @Autowired
    public SchedulingTasks(TaskScheduler taskExecutor) {
        this.executor = taskExecutor;
    }

    public List<Data_collector> getMetaData(){
        return this.listWebScrapeTasks;
    }

//    @Scheduled(cron = "0 00 00 * * *")
    @Scheduled(fixedRate = 100000, initialDelay = 30000)
    public void scheduledRun() throws IOException, ParseException {

        listWebScrapeTasks = schedulingJpaResource.findWebScrapeTasks();

        listApiTasks = schedulingJpaResource.findApiTasks();

        for (Data_collector webTask : listWebScrapeTasks){
            if (webTask.getFrequency() == null){
                System.out.println("No frequency value");
            }
            // every 30 sec
            else if (webTask.getFrequency() == 1){
                webTask.setProceed(true);
                executor.schedule(webScrapeJpaResource, new CronTrigger("*/5 * * * * *"));

//                executor.scheduleAtFixedRate(apiDataResource, Date.from(LocalDateTime.now().plusMinutes(0)
//                        .atZone(ZoneId.systemDefault()).toInstant()), 15000);
            }
            // every 15 min
            else if (webTask.getFrequency() == 2){
                webTask.setProceed(true);
                executor.schedule(webScrapeJpaResource, new CronTrigger("0 15 * * * *"));
            }
            // every hour
            else if (webTask.getFrequency() == 3){
                webTask.setProceed(true);
                executor.schedule(webScrapeJpaResource, new CronTrigger("0 0 * * * *"));
            }
            // every 6 hr
            else if (webTask.getFrequency() == 4){
                webTask.setProceed(true);
                executor.schedule(webScrapeJpaResource, new CronTrigger("0 0 0 */5 * *"));
            }
            // every day at 7 am
            else if (webTask.getFrequency() == 5){
                webTask.setProceed(true);
                executor.schedule(webScrapeJpaResource, new CronTrigger("0 0 7 * * *"));
            }
            // every week at Monday 7 am
            else if (webTask.getFrequency() == 6){
                webTask.setProceed(true);
                executor.schedule(webScrapeJpaResource, new CronTrigger("0 0 7 ? ? MON"));
            }
            // every first day of a month at 7 am
            else if (webTask.getFrequency() == 7){
                webTask.setProceed(true);
                executor.schedule(webScrapeJpaResource, new CronTrigger("0 0 7 1 * ?"));
            }
            else{
                System.out.println("Value is out of range. Please choose value between (1-7)");
            }
        }

        for (Data_collector api : listApiTasks){
//            System.out.println(api.toString());
        }

        System.out.println("We are in scheduledRun");
//        scheduling(null);

    }

}