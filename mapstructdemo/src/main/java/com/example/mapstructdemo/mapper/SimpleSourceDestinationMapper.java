package com.example.mapstructdemo.mapper;

import com.example.mapstructdemo.bean.SimpleDestination;
import com.example.mapstructdemo.bean.SimpleSource;
import org.mapstruct.Mapper;

@Mapper
public interface SimpleSourceDestinationMapper {
    SimpleDestination sourceToDestination(SimpleSource source);
    SimpleSource destinationToSource(SimpleDestination destination);
}