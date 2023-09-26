package com.example.mapstructdemo.service;

import com.example.mapstructdemo.bean.Car;
import com.example.mapstructdemo.bean.CarDTO;
import com.example.mapstructdemo.bean.Division;
import com.example.mapstructdemo.bean.DivisionDTO;
import com.example.mapstructdemo.bean.Employee;
import com.example.mapstructdemo.bean.EmployeeDTO;
import com.example.mapstructdemo.bean.SimpleDestination;
import com.example.mapstructdemo.bean.SimpleSource;
import com.example.mapstructdemo.mapper.CarsMapper;
import com.example.mapstructdemo.mapper.EmployeeMapper;
import com.example.mapstructdemo.mapper.SimpleDestinationMapperUsingInjectedService;
import com.example.mapstructdemo.mapper.SimpleSourceDestinationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("mapStructService")
public class MapStructServiceImpl implements MapStructService{
    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private SimpleSourceDestinationMapper simpleSourceDestinationMapper;

    @Autowired
    private CarsMapper carsMapper;

    @Autowired
    private SimpleDestinationMapperUsingInjectedService service;

    @Override
    public EmployeeDTO getEmployee(Employee employee) {
        return employeeMapper.employeeToEmployeeDTO(employee);
    }

    @Override
    public SimpleDestination getSimple(SimpleSource source) {
        return simpleSourceDestinationMapper.sourceToDestination(source);
    }

    @Override
    public DivisionDTO getDivision(Division division) {
        return employeeMapper.divisionToDivisionDTO(division);
    }

    @Override
    public SimpleDestination getSimple1(SimpleSource source) {
        return service.sourceToDestination(source);
    }

    @Override
    public CarDTO getCar(Car car) {
        return carsMapper.toCarDto(car);
    }
}
