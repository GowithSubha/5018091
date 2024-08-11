package com.employee.mysql.repository;

import com.employee.mysql.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Project, Integer> {

    Project findByName(String name);

}
