package com.example.mapstructdemo.mapper;

import com.example.mapstructdemo.bean.SimpleDestination;
import com.example.mapstructdemo.bean.SimpleSource;
import com.example.mapstructdemo.service.SimpleService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public abstract class SimpleDestinationMapperUsingInjectedService {

    @Autowired
    protected SimpleService simpleService;

    @Mapping(target = "name", expression = "java(simpleService.enrichName(source.getName()))")
    public abstract SimpleDestination sourceToDestination(SimpleSource source);
}