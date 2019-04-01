package com.employee.management.junit;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.employee.management.model.Employee;
import com.employee.management.repository.EmployeeRepository;
import com.employee.management.service.EmployeeService;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeDAOTestsGet
{
	EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
	
	@Mock
	EmployeeService employeeService = new EmployeeService();

	
	@Test
	public void getEmployeeById() {
	    UUID uuid = UUID.randomUUID();
		Employee employee = new Employee (uuid, "Sharat","Naik",LocalDate.of(1982, 11, 29) , "sharat@gmail.com", 1);
	     when(employeeRepository.findById(uuid)).thenReturn(Optional.of(employee)); 
	    Assert.assertEquals(uuid,employee.getUuid());
	}
	
	@Test
	public void getAllEmployees()
	{
		Employee employee = new Employee ("Sharat","Naik",LocalDate.of(1982, 11, 29) , "sharat@gmail.com", 1);
		List<Employee> employees = new ArrayList<Employee>();
		employees.add(employee);
		Mockito.when(employeeRepository.findAll()).thenReturn(employees);
		List<Employee> employeeAssert =  employeeRepository.findAll();
		assertEquals(employeeAssert, employees);
		verify(employeeRepository).findAll();
	}
        
}

