package com.employee.EMPLOYEE.repository;

import com.employee.EMPLOYEE.model.entity.Employee;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
