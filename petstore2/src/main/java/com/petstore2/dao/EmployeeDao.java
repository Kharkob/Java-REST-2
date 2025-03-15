package com.petstore2.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petstore2.entity.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Long> {

}
