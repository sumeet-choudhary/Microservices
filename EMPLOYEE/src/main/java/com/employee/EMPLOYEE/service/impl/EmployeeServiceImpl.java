package com.employee.EMPLOYEE.service.impl;

import com.employee.EMPLOYEE.exception.BadRequestException;
import com.employee.EMPLOYEE.exception.ResourceNotFoundException;
import com.employee.EMPLOYEE.model.dto.EmployeeDto;
import com.employee.EMPLOYEE.model.entity.Employee;
import com.employee.EMPLOYEE.repository.EmployeeRepository;
import com.employee.EMPLOYEE.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    public final EmployeeRepository employeeRepository;
    public final ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        if(employeeDto.getId()!=null){
            throw new BadRequestException("Employee already exists");
        }

        Employee entity = modelMapper.map(employeeDto, Employee.class);
        Employee saveEntity = employeeRepository.save(entity);
        return modelMapper.map(saveEntity, EmployeeDto.class);
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        if(id==null || employeeDto.getId()==null){
            throw new BadRequestException("Please provide Employee ID");
        }
        if(!Objects.equals(id,employeeDto.getId())){
            throw new BadRequestException("ID mismatch");
        }
        employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee not found with id : "+id));

        Employee entity = modelMapper.map(employeeDto, Employee.class);
        Employee updatedEmployee = employeeRepository.save(entity);
        return modelMapper.map(updatedEmployee, EmployeeDto.class);
    }

    @Override
    public void deleteEmployee(Long id) {
        if(id==null){
            throw new RuntimeException("Please provide Employee ID");
        }
        Employee employee = employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee not found with id : "+id));
        employeeRepository.delete(employee);
    }

    @Override
    public EmployeeDto getSingleEmployee(Long id) {
        if(id==null){
            throw new RuntimeException("Please provide Employee ID");
        }
        Employee employee = employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee not found with id : "+id));
        return modelMapper.map(employee, EmployeeDto.class);
    }

    @Override
    public List<EmployeeDto> getAllEmployee() {
        List<Employee> employees = employeeRepository.findAll();
        if(employees.isEmpty()){
            throw new ResourceNotFoundException("No employee found");
        }
        return employees.stream().map(emp -> modelMapper.map(emp, EmployeeDto.class)).toList();
    }
}
