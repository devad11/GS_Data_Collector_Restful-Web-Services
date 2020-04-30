package com.gs_data_collector.rest.webservices.restfulwebservices.service;

import com.gs_data_collector.rest.webservices.restfulwebservices.entities.User;

public interface UserServiceImp {
    User findByUsername(String username);
    User save (User user);
}
