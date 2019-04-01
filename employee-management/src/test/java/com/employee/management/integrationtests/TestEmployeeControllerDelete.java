package com.employee.management.integrationtests;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
import com.employee.management.model.Employee;
import com.employee.management.repository.DepartmentRepository;
import com.employee.management.repository.EmployeeRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class )
@Import(SecurityConfiguration.class)
public class TestEmployeeControllerDelete {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	EmployeeRepository employeeRepository;
	
	@MockBean
	DepartmentRepository departmentRepository;
	
	@MockBean
	KafkaTemplate<String, Event> kafkaTemplate;

	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "USER")
	public void testDeleteEmployeeById() throws Exception {
		UUID uuid = UUID.randomUUID();
		Employee employee1 = new Employee(uuid ,"Sharat","Naik",LocalDate.of(1982, 11, 29) , "sharat@gmail.com", 1);

		 Mockito.when(employeeRepository.findById(uuid))
         .thenReturn(Optional.of(employee1));
		 
		 RequestBuilder request = MockMvcRequestBuilders.delete("/api/v1/employees/{uuid}",uuid).accept(MediaType.APPLICATION_JSON);
	        mockMvc.perform(request).andExpect(status().isOk());
	       verify(employeeRepository, times(1)).deleteById(uuid);
	}
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "USER")
	public void testDeleteEmployees() throws Exception {
		Employee employee1 = new Employee("Sharat","Naik",LocalDate.of(1982, 11, 29) , "sharat@gmail.com", 1);
        Employee employee2 = new Employee("Sharat","Naik",LocalDate.of(1982, 11, 29) , "sharat@gmail.com", 1);
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1, employee2));
		 
		 RequestBuilder request = MockMvcRequestBuilders.delete("/api/v1/employees").accept(MediaType.APPLICATION_JSON);
	        mockMvc.perform(request).andExpect(status().isOk());
	       verify(employeeRepository, times(1)).deleteAll();
	}
	
}
	

