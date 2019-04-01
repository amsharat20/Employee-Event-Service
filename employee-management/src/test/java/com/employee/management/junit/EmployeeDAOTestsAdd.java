package com.employee.management.junit;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.UUID;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.employee.management.model.Employee;
import com.employee.management.repository.EmployeeRepository;
import com.employee.management.service.EmployeeService;


public class EmployeeDAOTestsAdd
{
	
	@InjectMocks
	private EmployeeService  employeeService;
    
 
	@Mock
	private EmployeeRepository employeeRepository;
	  
	@Captor
	private ArgumentCaptor<Employee> employeeArgument;
	
	  
	  public EmployeeDAOTestsAdd() {
	        MockitoAnnotations.initMocks(this);
	    }

	@Test
    public void addEmployee() throws UnsupportedEncodingException {
		  UUID uuid = UUID.randomUUID();
		employeeService.addEmployee(new Employee (uuid,"Sharat","Naik",LocalDate.of(1982, 11, 29) , "sharat@gmail.com", 1));
		verify(employeeRepository).save(employeeArgument.capture());
	    assertThat(employeeArgument.getValue().getUuid(), is(notNullValue()));
    }

}

