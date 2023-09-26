package com.example.mapstructdemo.service;

import org.springframework.stereotype.Service;

@Service("simpleService")
public class SimpleServiceImpl implements SimpleService {
    @Override
    public String enrichName(String name) {
        return "simple_service_" + name;
    }
}
