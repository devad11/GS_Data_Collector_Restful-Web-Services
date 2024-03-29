package com.gs_data_collector.rest.webservices.restfulwebservices.scheduling;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchedulerJpaRepository{

    List<Data_collector> findWebScrapeTasks();
    List<Data_collector> findApiTasks();
    void updateNextSchedule(String name, long time);
}
