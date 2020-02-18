package com.gs_data_collector.rest.webservices.restfulwebservices.jwt.resource;

import java.io.Serializable;

public class  JwtTokenRequest implements Serializable {
  
  private static final long serialVersionUID = -5616176897013108345L;

  private String username;
    private String password;
    
//    {
//    	"token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZGFtIiwiZXhwIjoxNTgxNDI5ODIzLCJpYXQiOjE1ODA4MjUwMjN9.I90bkDZmfBuTTueSwSpBvDZWensZUkOIcLM2tBWRiV5ShzzyUQtKiSMiCr66EPK0FgrMeyuWv649AHuNkNIo3g"
//    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZGFtIiwiZXhwIjoxNTgyNjQxNDUxLCJpYXQiOjE1ODIwMzY2NTF9.gZ_ptDZQ-DkpnCgUFpKKFGgpDW32zVoJCCFZIzp7q5t_AodPNioJCSh-U6H2A1rKPjA0u7hq8kkTHTCTD-HfGQ"
//    	}

    public JwtTokenRequest() {
        super();
    }

    public JwtTokenRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

