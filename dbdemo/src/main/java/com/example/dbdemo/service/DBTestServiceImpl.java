package com.example.dbdemo.service;

import com.example.dbdemo.domain.Data;
import com.example.dbdemo.mapper.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBTestServiceImpl implements DBTestService{
    @Autowired
    private DataMapper mapper;

    @Override
    public Data getData(long id) {
        return mapper.getDataById(id);
    }
}
