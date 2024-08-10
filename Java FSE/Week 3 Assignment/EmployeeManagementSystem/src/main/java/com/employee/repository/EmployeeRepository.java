package com.employee.repository;

import com.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Method to find employees by department with pagination and sorting
    Page<Employee> findByDepartmentName(String departmentName, Pageable pageable);

    // Method to search employees by name with pagination and sorting
    Page<Employee> findByNameContaining(String name, Pageable pageable);

    // Method to find high earning employees with pagination and sorting
    Page<Employee> findBySalaryGreaterThan(Double salary, Pageable pageable);

    // Custom query to find employees by department
    @Query("SELECT e FROM Employee e WHERE e.department.name = :departmentName")
    Page<Employee> findEmployeesByDepartment(@Param("departmentName") String departmentName, Pageable pageable);

    // Custom query to search employees by name
    @Query("SELECT e FROM Employee e WHERE e.name LIKE %:name%")
    Page<Employee> searchByName(@Param("name") String name, Pageable pageable);

    // Custom query to find high earning employees
    @Query("SELECT e FROM Employee e WHERE e.salary > :salary")
    Page<Employee> findHighEarningEmployees(@Param("salary") Double salary, Pageable pageable);
}
