package com.employee.management.junit;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.employee.management.model.Department;
import com.employee.management.repository.DepartmentRepository;
import com.employee.management.service.DepartmentService;


public class DepartmentDAOTestsAdd
{
	
	@InjectMocks
	private DepartmentService  departmentService;
 
	@Mock
	  private DepartmentRepository departmentDAO;
	  
	@Captor
	    private ArgumentCaptor<Department> customerArgument;
	
	  
	  public DepartmentDAOTestsAdd() {
	        MockitoAnnotations.initMocks(this);
	    }

	@Test
    public void addEmployee() {
		departmentService.addDepartment(new Department(1,"Test"));
		verify(departmentDAO).save(customerArgument.capture());
	    assertThat(customerArgument.getValue().getDepartment_ID(), is(notNullValue()));
    }

}

