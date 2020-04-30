package com.gs_data_collector.rest.webservices.restfulwebservices.jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.gs_data_collector.rest.webservices.restfulwebservices.entities.User;
import com.gs_data_collector.rest.webservices.restfulwebservices.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtInMemoryUserDetailsService implements UserDetailsService {

  @Autowired
  UserServiceImp userServiceImp;

  static List<JwtUserDetails> inMemoryUserList = new ArrayList<>();

  static {
    inMemoryUserList.add(new JwtUserDetails(1L, "adam",
        "$2a$10$3zHzb.Npv1hfZbLEU5qsdOju/tk2je6W6PnNnY.c1ujWPcZh4PL6e", "ROLE_USER_2"));
    inMemoryUserList.add(new JwtUserDetails(1L, "devad",
            "$2a$10$VwqQw.xsubrEmxRYjhAOpuP/wBgWiBM9iJClJKXgh5bU/eommzz1O", "ROLE_USER_2"));
      
  }

  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//    Optional<JwtUserDetails> findFirst = inMemoryUserList.stream()
//        .filter(user -> user.getUsername().equals(username)).findFirst();
//
//    if (!findFirst.isPresent()) {
//      throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username));
//    }

//    return findFirst.get();
    User user = userServiceImp.findByUsername(username);
    UserDetails userDetails = (new JwtUserDetails(user.getId(), user.getUsername(),
            user.getPassword(), user.getRole()));

    return userDetails;
  }

}


