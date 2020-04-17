package com.gs_data_collector.rest.webservices.restfulwebservices.scheduling;

import java.util.List;

public interface SchedulerJpaRepository {

    List<Data_collector> findWebScrapeTasks();
    List<Data_collector> findApiTasks();
}
