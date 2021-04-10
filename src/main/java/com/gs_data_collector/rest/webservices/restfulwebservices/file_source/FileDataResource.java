package com.gs_data_collector.rest.webservices.restfulwebservices.file_source;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class FileDataResource {

    @PostMapping("/sendfile")
    public String apiDataReceiver(
            @RequestBody File apiData){

        System.out.println(apiData);

        return "Hello world";
    }
}
