package com.employee.management.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.employee.management.controller.DepartmentController;
import com.employee.management.model.Department;
import com.employee.management.service.DepartmentService;
import com.google.common.base.Verify;

@RunWith(MockitoJUnitRunner.class)
public class TestDepartmentController {
	
	@Mock
    DepartmentService departmentService;
	
    @InjectMocks
    DepartmentController departmentController = new DepartmentController();
    
    
    @Test
    public void testInsertDepartment(){

        Department department = new Department();

        departmentController.addDepartment(department);

        //verify(departmentService,times(1)).addDepartment(department);
    }

}
