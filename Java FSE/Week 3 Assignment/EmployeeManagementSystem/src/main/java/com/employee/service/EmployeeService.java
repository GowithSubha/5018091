package com.employee.service;

import com.employee.entity.primary.Employee;
import com.employee.dto.EmployeeDto;

import com.employee.repository.primary.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Page<Employee> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public Page<EmployeeDto> getEmployeeDtosByDepartment(String departmentName, Pageable pageable) {
        return employeeRepository.findEmployeeDtosByDepartment(departmentName, pageable);
    }

    public Page<EmployeeDto> searchEmployeeDtosByName(String name, Pageable pageable) {
        return employeeRepository.searchEmployeeDtosByName(name, pageable);
    }

    public Page<EmployeeDto> getHighSalaryEmployeeDtos(Double salary, Pageable pageable) {
        return employeeRepository.findHighSalaryEmployeeDtos(salary, pageable);
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id).orElseThrow();
        employee.setName(employeeDetails.getName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setDepartment(employeeDetails.getDepartment());
        employee.setSalary(employeeDetails.getSalary());
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
