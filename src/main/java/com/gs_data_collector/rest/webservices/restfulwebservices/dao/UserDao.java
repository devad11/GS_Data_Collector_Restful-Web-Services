package com.gs_data_collector.rest.webservices.restfulwebservices.dao;
import com.gs_data_collector.rest.webservices.restfulwebservices.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    User findByUsername(String username);

}
