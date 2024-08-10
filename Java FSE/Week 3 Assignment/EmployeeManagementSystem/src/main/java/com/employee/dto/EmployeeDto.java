package com.employee.dto;

public class EmployeeDto {
    private Long id;
    private String name;
    private Double salary;
    private String departmentName;

    public EmployeeDto() {
    }

    public EmployeeDto(Long id, String name, Double salary, String departmentName) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.departmentName = departmentName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

}
