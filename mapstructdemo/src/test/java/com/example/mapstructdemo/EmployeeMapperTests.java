package com.example.mapstructdemo;

import com.example.mapstructdemo.bean.DivisionDTO;
import com.example.mapstructdemo.bean.Employee;
import com.example.mapstructdemo.bean.EmployeeDTO;
import com.example.mapstructdemo.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeMapperTests {
    private EmployeeMapper mapper = Mappers.getMapper(EmployeeMapper.class);
    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";
    @Test
    public void givenEmployeeDTOwithDiffNametoEmployee_whenMaps_thenCorrect() {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmployeeId(1);
        dto.setEmployeeName("John");
        dto.setDivision(new DivisionDTO(1, "Division1"));

        Employee entity = mapper.employeeDTOtoEmployee(dto);

        assertEquals(dto.getEmployeeId(), entity.getId());
        assertEquals(dto.getEmployeeName(), entity.getName());
        assertEquals(dto.getDivision().getId(),
                entity.getDivision().getId());
        assertEquals(dto.getDivision().getName(),
                entity.getDivision().getName());
    }

    @Test
    public void givenEmpStartDtMappingToEmpDTO_whenMaps_thenCorrect() throws ParseException {
        Employee entity = new Employee();
        entity.setStartDt(new Date());
        EmployeeDTO dto = mapper.employeeToEmployeeDTO(entity);
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);

        assertEquals(format.parse(dto.getEmployeeStartDt()).toString(),
                entity.getStartDt().toString());
    }
    @Test
    public void givenEmpDTOStartDtMappingToEmp_whenMaps_thenCorrect() throws ParseException {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmployeeStartDt("01-04-2016 01:00:00");
        Employee entity = mapper.employeeDTOtoEmployee(dto);
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);

        assertEquals(format.parse(dto.getEmployeeStartDt()).toString(),
                entity.getStartDt().toString());
    }
}
