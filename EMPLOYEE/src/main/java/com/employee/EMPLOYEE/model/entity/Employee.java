package com.employee.EMPLOYEE.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String empName;
    private String empCode;
    private String empEmail;
    private String companyName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", empName='" + empName + '\'' +
                ", empCode='" + empCode + '\'' +
                ", empEmail='" + empEmail + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }

    public Employee() {
    }

    public Employee(Long id, String empName, String empCode, String empEmail, String companyName) {
        this.id = id;
        this.empName = empName;
        this.empCode = empCode;
        this.empEmail = empEmail;
        this.companyName = companyName;
    }

}
