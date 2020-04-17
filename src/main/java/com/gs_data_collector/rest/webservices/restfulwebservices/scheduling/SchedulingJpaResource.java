package com.gs_data_collector.rest.webservices.restfulwebservices.scheduling;

import com.gs_data_collector.rest.webservices.restfulwebservices.dao.SchedulerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SchedulingJpaResource implements SchedulerJpaRepository{

    @Autowired
    SchedulerDao schedulerDao;

    @Override
    public List<Data_collector> findWebScrapeTasks() {
        return schedulerDao.findTasks(2);
    }

    @Override
    public List<Data_collector> findApiTasks() {
        return schedulerDao.findTasks(1);
    }
}
