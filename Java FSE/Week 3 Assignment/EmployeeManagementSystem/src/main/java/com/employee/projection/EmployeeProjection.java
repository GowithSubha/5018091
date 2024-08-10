package com.employee.projection;

public interface EmployeeProjection {
    Long getId();

    String getName();

    Double getSalary();

    String getDepartmentName(); // Custom property to fetch department name
}