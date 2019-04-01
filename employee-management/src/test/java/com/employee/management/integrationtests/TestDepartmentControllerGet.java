package com.employee.management.integrationtests;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.employee.management.controller.EmployeeController;
import com.employee.management.kafkaservice.Event;
import com.employee.management.model.Department;
import com.employee.management.repository.DepartmentRepository;
import com.employee.management.repository.EmployeeRepository;


@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class )
@Import(SecurityConfiguration.class)
public class TestDepartmentControllerGet {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	DepartmentRepository departmentRepository;
	
	@MockBean
	EmployeeRepository employeeRepository;
	
	@MockBean
	KafkaTemplate<String, Event> kafkaTemplate;
	

	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "USER")
	public void testGetDepartment() throws Exception {
		Department dept = new Department(1,"testDept");
		when(departmentRepository.findById(1)).thenReturn(Optional.of(dept));
		RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/departments/1").accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(request).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "USER")
	public void testGetAllDepartments() throws Exception {
		Department dept1 = new Department(1,"testDept");
		Department dept2 = new Department(1,"testDept");
        when(departmentRepository.findAll()).thenReturn(Arrays.asList(dept1, dept2));
        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/departments").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isOk());
	}
	
}
