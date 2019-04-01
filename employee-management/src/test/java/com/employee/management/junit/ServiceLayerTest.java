package com.employee.management.junit;

import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.employee.management.model.Department;
import com.employee.management.model.Employee;
import com.employee.management.repository.DepartmentRepository;
import com.employee.management.repository.EmployeeRepository;
import com.employee.management.service.EmployeeService;


@RunWith(MockitoJUnitRunner.class)
public class ServiceLayerTest {
	
	@Spy
	private EmployeeService employeeService;
	@Mock
	private EmployeeRepository employeeRepository;
	@Mock
	private Employee employee;
	@Mock
	private Department department;
	@Mock
	private DepartmentRepository departmentRepository;


	@Test
	public void verifyfindById() throws Exception {
		UUID id = UUID.randomUUID();
		Mockito.doReturn(Optional.of(employee)).when(employeeService).getEmployee(id);
		Optional<Employee> e = employeeService.getEmployee(id);
		Mockito.verify(employeeService).getEmployee(id);
	}
}
