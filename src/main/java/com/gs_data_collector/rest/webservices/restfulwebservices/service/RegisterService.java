package com.gs_data_collector.rest.webservices.restfulwebservices.service;

import com.gs_data_collector.rest.webservices.restfulwebservices.dao.RegisterDao;
import com.gs_data_collector.rest.webservices.restfulwebservices.dao.UserDao;
import com.gs_data_collector.rest.webservices.restfulwebservices.emailService.SendEmailService;
import com.gs_data_collector.rest.webservices.restfulwebservices.entities.Register;
import com.gs_data_collector.rest.webservices.restfulwebservices.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@Service
public class RegisterService {

    @Autowired
    private SendEmailService sendEmailService;

    @Autowired
    private RegisterDao registerDao;

    @Autowired
    private UserDao userDao;

    @PostMapping("/register")
    public ResponseEntity<Void> accessRequest(
            @RequestBody Register regInfo){

        if (! registerDao.existsByEmail(regInfo.getEmail()))
            regInfo.setPassword(getPassword(regInfo.getPassword()));
            registerDao.save(regInfo);

        String subject = "GS Data Collector Access Request (" + regInfo.getName() + ")";
        String body = regInfo.getName() + " would like access to GS Data Collector for the following reason : "
                + regInfo.getReason() + "\n"
                + "Please after the request assessed contact this employee on the following Email address: \n"
                + regInfo.getEmail() + "\n Thank you!\n"
                + "http://localhost:4200/request";

        sendEmailService.sendFromGMail(subject, body);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/requests")
    public List<Register> getAllTodos(){
        return registerDao.findAllByAcceptedIsFalse();
    }


    @DeleteMapping("/request/{id}/accept/{res}")
    public  ResponseEntity<Void> UpdateReg(@PathVariable long id, @PathVariable Boolean res){

        if(res == true){
            registerDao.accept(id);
            Register acceptedUser = registerDao.findById(id);
            userDao.save(new User(acceptedUser.getUsername(),acceptedUser.getPassword(), "ROLE_USER_2"));

        }
        else{
            registerDao.removeRegisterById(id);
        }

        return ResponseEntity.noContent().build();
    }

    public String getPassword(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            String encodedString = encoder.encode(password);
            return encodedString;
        }

}
