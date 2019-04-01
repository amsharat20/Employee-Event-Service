package com.employee.management.junit;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.employee.management.model.Department;
import com.employee.management.model.Employee;
import com.employee.management.repository.DepartmentRepository;
import com.employee.management.service.DepartmentService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class DepartmentDAOTestsGetAll
{
	@Mock
	DepartmentRepository personRepository = mock(DepartmentRepository.class);

	@InjectMocks
	DepartmentService personService = new DepartmentService();
	
	@Mock
	  Department department ;
	
	
	@Test
	public void getAllEmployees()
	{
		Department dept = new Department(1,"Test");
		List<Department> depts = new ArrayList<Department>();
		depts.add(dept);
		Mockito.when(personRepository.findAll()).thenReturn(depts);
		List<Department> employeeAssert =  (List<Department>) personRepository.findAll();
		assertEquals(employeeAssert, depts);
		verify(personRepository).findAll();
		
	}
        

}

