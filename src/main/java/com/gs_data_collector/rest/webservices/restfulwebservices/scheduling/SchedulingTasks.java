package com.gs_data_collector.rest.webservices.restfulwebservices.scheduling;

import com.gs_data_collector.rest.webservices.restfulwebservices.api_source.ApiDataResource;
import com.gs_data_collector.rest.webservices.restfulwebservices.emailService.SendEmailService;
import com.gs_data_collector.rest.webservices.restfulwebservices.web_scraping.WebScrapeJpaResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class SchedulingTasks {

    @Autowired
    private SendEmailService sendEmailService;
    @Autowired
    private SchedulerJpaRepository schedulerJpaRepository;
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

    public List<Data_collector> getWebMetaData(){
        return this.listWebScrapeTasks;
    }
    public List<Data_collector> getApiMetaData(){
        return this.listApiTasks;
    }

    @Scheduled(fixedRate = 30000)
    public void scheduledRun() throws IOException, ParseException {

        System.out.println("List update");
        listWebScrapeTasks = schedulerJpaRepository.findWebScrapeTasks();
        listApiTasks = schedulerJpaRepository.findApiTasks();
        Date date= new Date();
        long time = date.getTime();
        System.out.println(time);

        for (Data_collector webTask : listWebScrapeTasks){
            if (webTask.getFrequency() == null){
                System.out.println("No frequency value");
        }
            // only once
            else if (webTask.getFrequency() == 0){
                webTask.setProceed(true);
                webScrapeJpaResource.run();

            }
            // every 30 sec
            else if (webTask.getFrequency() == 1){
                webTask.setProceed(true);
                System.out.println("calculation: " + (webTask.getCreated() - time));
                if (webTask.getCreated() - time <= 0) {
                    webScrapeJpaResource.run();
                    time += 30*1000;
                    schedulerJpaRepository.updateNextSchedule(webTask.getName(), time);
                }
            }
            // every 15 min
            else if (webTask.getFrequency() == 2){
                webTask.setProceed(true);
                System.out.println("calculation: " + (webTask.getCreated() - time));
                if (webTask.getCreated() - time <= 0) {
                    webScrapeJpaResource.run();
                    time += 900*1000;
                    schedulerJpaRepository.updateNextSchedule(webTask.getName(), time);
                }
            }
            // every hour
            else if (webTask.getFrequency() == 3){
                webTask.setProceed(true);
                System.out.println("calculation: " + (webTask.getCreated() - time));
                if (webTask.getCreated() - time <= 0) {
                    webScrapeJpaResource.run();
                    time += 3600*1000;
                    schedulerJpaRepository.updateNextSchedule(webTask.getName(), time);
                }
            }
            // every 6 hr
            else if (webTask.getFrequency() == 4){
                webTask.setProceed(true);
                System.out.println("calculation: " + (webTask.getCreated() - time));
                if (webTask.getCreated() - time <= 0) {
                    webScrapeJpaResource.run();
                    time += 21600*1000;
                    schedulerJpaRepository.updateNextSchedule(webTask.getName(), time);
                }
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
                System.out.println("Value is out of range. Please choose value between (0-7)");
            }
        }

        for (Data_collector apiTask : listApiTasks){
            if (apiTask.getFrequency() == null){
                System.out.println("No frequency value");
            }
            // every 30 sec
            else if (apiTask.getFrequency() == 1){
                apiTask.setProceed(true);
                System.out.println("calculation: " + (apiTask.getCreated() - time));
                if (apiTask.getCreated() - time <= 0) {
                    apiDataResource.run();
                    time += 30*1000;
                    schedulerJpaRepository.updateNextSchedule(apiTask.getName(), time);
                }

            }
            // every 15 min
            else if (apiTask.getFrequency() == 2){
                apiTask.setProceed(true);
                System.out.println("calculation: " + (apiTask.getCreated() - time));
                if (apiTask.getCreated() - time <= 0) {
                    apiDataResource.run();
                    time += 90 * 1000;
                    schedulerJpaRepository.updateNextSchedule(apiTask.getName(), time);
                }
            }
            // every hour
            else if (apiTask.getFrequency() == 3){
                apiTask.setProceed(true);
                executor.schedule(apiDataResource, new CronTrigger("0 0 * * * *"));
            }
            // every 6 hr
            else if (apiTask.getFrequency() == 4){
                apiTask.setProceed(true);
                executor.schedule(apiDataResource, new CronTrigger("0 0 0 */5 * *"));
            }
            // every day at 7 am
            else if (apiTask.getFrequency() == 5){
                apiTask.setProceed(true);
                executor.schedule(apiDataResource, new CronTrigger("0 0 7 * * *"));
            }
            // every week at Monday 7 am
            else if (apiTask.getFrequency() == 6){
                apiTask.setProceed(true);
                executor.schedule(apiDataResource, new CronTrigger("0 0 7 ? ? MON"));
            }
            // every first day of a month at 7 am
            else if (apiTask.getFrequency() == 7){
                apiTask.setProceed(true);
                executor.schedule(apiDataResource, new CronTrigger("0 0 7 1 * ?"));
            }
            else{
                System.out.println("Value is out of range. Please choose value between (1-7)");
            }

        }

    }

}