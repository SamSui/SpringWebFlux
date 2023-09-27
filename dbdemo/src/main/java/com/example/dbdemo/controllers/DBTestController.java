package com.example.dbdemo.controllers;

import com.example.dbdemo.domain.Data;
import com.example.dbdemo.service.DBTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/db")
public class DBTestController {
    @Autowired
    private DBTestService service;

    @GetMapping("/get")
    public String getData(){
        Data data = service.getData(46772l);
        if(data == null){
            return "fail";
        }
        return data.getName() +"_"+ data.getId();
    }
}
