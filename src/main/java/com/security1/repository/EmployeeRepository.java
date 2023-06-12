package com.security1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security1.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
