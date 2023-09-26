package com.example.mapstructdemo.mapper;

import com.example.mapstructdemo.bean.BioDieselCar;
import com.example.mapstructdemo.bean.Car;
import com.example.mapstructdemo.bean.CarDTO;
import com.example.mapstructdemo.bean.ElectricCar;
import com.example.mapstructdemo.bean.FuelType;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public abstract class CarsMapper {
    @BeforeMapping
    protected void enrichDTOWithFuelType(Car car, @MappingTarget CarDTO carDto) {
        if (car instanceof ElectricCar) {
            carDto.setFuelType(FuelType.ELECTRIC);
        }
        if (car instanceof BioDieselCar) {
            carDto.setFuelType(FuelType.BIO_DIESEL);
        }
    }

    @AfterMapping
    protected void convertNameToUpperCase(@MappingTarget CarDTO carDto) {
        carDto.setName(carDto.getName().toUpperCase());
    }

    public abstract CarDTO toCarDto(Car car);
}