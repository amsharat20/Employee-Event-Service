package com.employee.management.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.management.model.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, UUID>{

	
}
