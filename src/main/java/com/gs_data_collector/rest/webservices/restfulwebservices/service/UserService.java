package com.gs_data_collector.rest.webservices.restfulwebservices.service;

import com.gs_data_collector.rest.webservices.restfulwebservices.dao.UserDao;
import com.gs_data_collector.rest.webservices.restfulwebservices.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserServiceImp{

    @Autowired
    UserDao userDao;

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User save(User user) {
        userDao.save(user);
        return user;
    }
}
