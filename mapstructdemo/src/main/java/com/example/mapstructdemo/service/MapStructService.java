package com.example.mapstructdemo.service;

import com.example.mapstructdemo.bean.Car;
import com.example.mapstructdemo.bean.CarDTO;
import com.example.mapstructdemo.bean.Division;
import com.example.mapstructdemo.bean.DivisionDTO;
import com.example.mapstructdemo.bean.Employee;
import com.example.mapstructdemo.bean.EmployeeDTO;
import com.example.mapstructdemo.bean.SimpleDestination;
import com.example.mapstructdemo.bean.SimpleSource;

public interface MapStructService {
    EmployeeDTO getEmployee(Employee employee);
    SimpleDestination getSimple(SimpleSource source);
    DivisionDTO getDivision(Division division);
    SimpleDestination getSimple1(SimpleSource source);
    CarDTO getCar(Car car);
}
