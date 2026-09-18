package com.employee.EMPLOYEE.controller;

import com.employee.EMPLOYEE.model.dto.EmployeeDto;
import com.employee.EMPLOYEE.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController (EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getSingleEmployee(@PathVariable Long id){
        EmployeeDto response = employeeService.getSingleEmployee(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    };

    @GetMapping("/all")
    public ResponseEntity<Iterable<EmployeeDto>> getAllEmployee(){
        Iterable<EmployeeDto> response = employeeService.getAllEmployee();
        return new ResponseEntity<>(response, HttpStatus.OK);
    };

    @PostMapping("/save")
    public ResponseEntity<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto){
        EmployeeDto response = employeeService.saveEmployee(employeeDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    };

    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable Long id){
        EmployeeDto response = employeeService.updateEmployee(id, employeeDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>("Employee Deleted Successfully", HttpStatus.OK);
    }

}
