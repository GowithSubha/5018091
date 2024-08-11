package com.employee.service;

import com.employee.dto.DepartmentDto;
import com.employee.postgresql.entity.Department;
import com.employee.postgresql.repository.DepartmentRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Page<DepartmentDto> getAllDepartmentDtos(Pageable pageable) {
        return departmentRepository.findAllDepartmentDtos(pageable);
    }

    public Page<DepartmentDto> getDepartmentDtosByName(String name, Pageable pageable) {
        return departmentRepository.findDepartmentDtosByName(name, pageable);
    }

    public Department updateDepartment(Long id, Department departmentDetails) {
        Department department = departmentRepository.findById(id).orElseThrow();
        department.setName(departmentDetails.getName());
        return departmentRepository.save(department);
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}