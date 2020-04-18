package com.gs_data_collector.rest.webservices.restfulwebservices.dao;

import com.gs_data_collector.rest.webservices.restfulwebservices.scheduling.Data_collector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SchedulerDao extends JpaRepository<Data_collector, Integer> {

    @Query(value = "SELECT * FROM Data_collector Where collection_type = :typeCode", nativeQuery=true)
    List<Data_collector> findTasks(@Param("typeCode") int typeCode);

}