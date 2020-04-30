package com.gs_data_collector.rest.webservices.restfulwebservices.service;

import com.gs_data_collector.rest.webservices.restfulwebservices.emailService.SendEmailService;
import com.gs_data_collector.rest.webservices.restfulwebservices.forms.Register;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class RegisterService {

    @Autowired
    private SendEmailService sendEmailService;

    @PostMapping("/register")
    public ResponseEntity<Void> accessRequest(
            @RequestBody Register regInfo){

        String subject = "GS Data Collector Access Request (" + regInfo.getName() + ")";
        String body = regInfo.getName() + " would like access to GS Data Collector for the following reason : "
                + regInfo.getReason() + "\n"
                + "Please after the request assessed contact this employee on the following eamil address: \n"
                + regInfo.getEmail() + "\n Thank you!";

        sendEmailService.sendFromGMail(subject, body);

        return ResponseEntity.noContent().build();
    }
}
