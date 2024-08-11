package com.employee.postgresql.repository;

import com.employee.dto.DepartmentDto;
import com.employee.postgresql.entity.Department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    // Query method to find department by its name
    @Query("SELECT new com.employee.dto.DepartmentDto(d.id, d.name) FROM Department d")
    Page<DepartmentDto> findAllDepartmentDtos(Pageable pageable);

    // Query method to find department by its name and project the result into a
    // custom projection with pagination
    @Query("SELECT new com.employee.dto.DepartmentDto(d.id, d.name) FROM Department d WHERE d.name = :name")
    Page<DepartmentDto> findDepartmentDtosByName(@Param("name") String name, Pageable pageable);

}