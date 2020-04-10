package com.gs_data_collector.rest.webservices.restfulwebservices.api_source;

import com.gs_data_collector.rest.webservices.restfulwebservices.todo.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface apiDataJpaRepository extends JpaRepository<Todo, Long>{
	
	List<Todo> findByUsername(String username);

}
