package com.employee.mysql.repository;

import com.employee.mysql.entity.Project;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;




public interface ProductRepository extends JpaRepository<Project, Integer> {

    Project findByName(String name);



}
