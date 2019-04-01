package com.employee.management.integrationtests;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

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
import com.employee.management.model.Employee;
import com.employee.management.repository.DepartmentRepository;
import com.employee.management.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class )
@Import(SecurityConfiguration.class)
public class TestEmployeeControllerPut {

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
	public void testPostEmployee() throws Exception {

		Department dept = new Department(1,"testDeptName");
		UUID uuid = UUID.randomUUID();
		Employee employee = new Employee(uuid, "Sharat","Naik",LocalDate.of(1989, 2, 27) , "sharat@gmail.com", 1);

		when(employeeRepository.findById(uuid)).thenReturn(Optional.of(employee));
		when(departmentRepository.findById(1)).thenReturn(Optional.of(dept));

		RequestBuilder request = MockMvcRequestBuilders.post("/api/v1/employees")
				.content(asJsonString(employee))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(request).andExpect(status().is2xxSuccessful());
	}

	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "USER")
	public void testPutEmployee() throws Exception {
		UUID uuid = UUID.randomUUID();
		Employee employee = new Employee(uuid, "Sharat","Naik",LocalDate.of(1989, 2, 27) , "sharat@gmail.com", 1);
		Department dept = new Department(1,"testDeptName");
	
		when(employeeRepository.findById(employee.getUuid())).thenReturn(Optional.of(employee));
		when(departmentRepository.findById(1)).thenReturn(Optional.of(dept));
		RequestBuilder request = MockMvcRequestBuilders.put("/api/v1/employees/{id}",employee.getUuid())
				.content(asJsonString(employee))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(request).andExpect(status().is2xxSuccessful());
	}

	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			System.out.println(jsonContent);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
