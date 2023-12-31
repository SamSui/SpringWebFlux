package com.example.mapstructdemo.mapper;

import com.example.mapstructdemo.bean.Division;
import com.example.mapstructdemo.bean.DivisionDTO;
import com.example.mapstructdemo.bean.Employee;
import com.example.mapstructdemo.bean.EmployeeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface EmployeeMapper {

    @Mapping(target="employeeId", source = "entity.id")
    @Mapping(target="employeeName", source = "entity.name")
    @Mapping(target="employeeStartDt", source = "entity.startDt",
            dateFormat = "dd-MM-yyyy HH:mm:ss")
    EmployeeDTO employeeToEmployeeDTO(Employee entity);

    @Mapping(target="id", source="dto.employeeId")
    @Mapping(target="name", source="dto.employeeName")
    @Mapping(target="startDt", source="dto.employeeStartDt",
            dateFormat="dd-MM-yyyy HH:mm:ss")
    Employee employeeDTOtoEmployee(EmployeeDTO dto);

    DivisionDTO divisionToDivisionDTO(Division entity);

    Division divisionDTOtoDivision(DivisionDTO dto);
}