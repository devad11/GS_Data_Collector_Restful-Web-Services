package com.gs_data_collector.rest.webservices.restfulwebservices.scheduling;

import com.gs_data_collector.rest.webservices.restfulwebservices.dao.SchedulerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins="http://localhost:4200")
@RestController
public class SchedulingJpaResource implements SchedulerJpaRepository{

    @Autowired
    SchedulerDao schedulerDao;

    public List<Data_collector> findWebScrapeTasks() {
        return schedulerDao.findTasks(2);
    }

    public List<Data_collector> findApiTasks() {
        return schedulerDao.findTasks(1);
    }

    @Override
    public void updateNextSchedule(String name, long time) {
        schedulerDao.updateTime(name, time);
    }

    @GetMapping("/info/{id}")
    public Data_collector getDataCollector(@PathVariable long id){
        return schedulerDao.findById(id);
    }

    @PutMapping("/updateinfo/{id}")
    public ResponseEntity<Data_collector> updateInfo(
            @PathVariable long id,
            @RequestBody Data_collector data_collector){

        Data_collector data_collectorUpdate = schedulerDao.save(data_collector);

        return new ResponseEntity<Data_collector>(data_collector, HttpStatus.OK);
    }

    @DeleteMapping("/deleteinfo/{id}")
    public  ResponseEntity<Void> deleteTodo(@PathVariable long id){
        schedulerDao.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
