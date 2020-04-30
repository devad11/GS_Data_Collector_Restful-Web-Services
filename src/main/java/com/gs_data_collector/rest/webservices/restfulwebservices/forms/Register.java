package com.gs_data_collector.rest.webservices.restfulwebservices.forms;

public class Register {

    private String name;
    private String reason;
    private String email;

    public Register() {
    }

    public Register(String name, String reason, String email) {
        this.name = name;
        this.reason = reason;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
