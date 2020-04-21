package com.gs_data_collector.rest.webservices.restfulwebservices.web_scraping;

import com.gs_data_collector.rest.webservices.restfulwebservices.scheduling.Data_collector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebJpaRepository extends JpaRepository<Data_collector, Long> {

}
