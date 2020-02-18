package com.gs_data_collector.rest.webservices.restfulwebservices.web_scraping;

import com.gs_data_collector.rest.webservices.restfulwebservices.todo.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebJpaRepository extends JpaRepository<WebScrape, Long> {

}
