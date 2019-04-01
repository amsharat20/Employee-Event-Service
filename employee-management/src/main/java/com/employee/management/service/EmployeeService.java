package com.employee.management.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.employee.management.model.Employee;
import com.employee.management.repository.EmployeeRepository;


@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	public List<Employee> getAllEmployees(){
		List<Employee> emp = (List<Employee>)employeeRepository.findAll(); 
		return emp;
	}
	
	
	  public Optional<Employee> getEmployee(UUID id){
		  return employeeRepository.findById(id);
		  }
	 
	  
	
	public void addEmployee(Employee emp) throws UnsupportedEncodingException {
		employeeRepository.save(emp);
	}
	

	public void updateEmployee(Employee emp, UUID id){
		if(id == emp.getUuid()) {
			employeeRepository.save(emp);
		}
	}
	
	public void deleteAllEmployees(){
		employeeRepository.deleteAll();
	}
	
	
	  public void deleteEmployeeByID(UUID id){
		  employeeRepository.deleteById(id); 
	  }
}
