package com.employee.h2.repository;

import com.employee.dto.EmployeeDto;
import com.employee.h2.entity.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

        // Custom query to find employees by department with pagination and sorting
        @Query("SELECT new com.employee.dto.EmployeeDto(e.id, e.fullName, e.salary, e.department.name) " +
                        "FROM Employee e WHERE e.department.name = :departmentName")
        Page<EmployeeDto> findEmployeeDtosByDepartment(@Param("departmentName") String departmentName,
                        Pageable pageable);

        // Custom query to search employees by name with pagination and sorting
        @Query("SELECT new com.employee.dto.EmployeeDto(e.id, e.fullName, e.salary, e.department.name) " +
                        "FROM Employee e WHERE e.fullName LIKE %:name%")
        Page<EmployeeDto> searchEmployeeDtosByName(@Param("name") String name, Pageable pageable);

        // Custom query to find high earning employees with pagination and sorting
        @Query("SELECT new com.employee.dto.EmployeeDto(e.id, e.fullName, e.salary, e.department.name) " +
                        "FROM Employee e WHERE e.salary > :salary")
        Page<EmployeeDto> findHighSalaryEmployeeDtos(@Param("salary") Double salary, Pageable pageable);


        @Query("delete from Employee e")
        void clear();

}
