package com.example.mapstructdemo.controllers;

import com.example.mapstructdemo.bean.BioDieselCar;
import com.example.mapstructdemo.bean.Car;
import com.example.mapstructdemo.bean.CarDTO;
import com.example.mapstructdemo.bean.Division;
import com.example.mapstructdemo.bean.DivisionDTO;
import com.example.mapstructdemo.bean.ElectricCar;
import com.example.mapstructdemo.bean.Employee;
import com.example.mapstructdemo.bean.EmployeeDTO;
import com.example.mapstructdemo.bean.SimpleDestination;
import com.example.mapstructdemo.bean.SimpleSource;
import com.example.mapstructdemo.service.MapStructService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mapstruct")
public class MapStructController {
    @Autowired
    private MapStructService mapStructService;

    @GetMapping("/employee")
    public EmployeeDTO getEmployee(){
        Employee employee = new Employee();
        employee.setId(2);
        employee.setName("employee");
        Division division = new Division();
        division.setName("division");
        employee.setDivision(division);
        return mapStructService.getEmployee(employee);
    }

    @GetMapping("/simple")
    public SimpleDestination getSimple(){
        SimpleSource source = new SimpleSource();
        source.setDescription("source_desc");
        source.setName("sourceName");
        return mapStructService.getSimple(source);
    }

    @GetMapping("/simple1")
    public SimpleDestination getSimple1(){
        SimpleSource source = new SimpleSource();
        source.setDescription("source_desc");
        source.setName("sourceName");
        return mapStructService.getSimple1(source);
    }

    @GetMapping("/division")
    public DivisionDTO getDivision(){
        Division division = new Division();
        division.setName("divison_name");
        division.setId(23);
        return mapStructService.getDivision(division);
    }

    @GetMapping("/car")
    public CarDTO getCar(){
        Car car = new ElectricCar();
        car.setName("ElectricCar");
        return mapStructService.getCar(car);
    }

    @GetMapping("/car2")
    public CarDTO getCar2(){
        Car car = new BioDieselCar();
        car.setName("BioDieselCar");
        return mapStructService.getCar(car);
    }
}
