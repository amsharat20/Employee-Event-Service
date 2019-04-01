package com.employee.management.junit;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
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
@WebMvcTest(EmployeeController.class)
public class TestEmployeeController {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	EmployeeRepository employeeRepository;
	
	@MockBean
	DepartmentRepository departmentRepository;
	
	@MockBean
	KafkaTemplate<String, Event> kafkaTemplate;

	@Test
	public void testGetEmployee() throws Exception {

		Employee employee = new Employee("Sharat","Naik",LocalDate.of(1988, 2, 20) , "sharat@gmail.com", 1);
		when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
		RequestBuilder request = MockMvcRequestBuilders.get("/employees/1").accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(request).andExpect(status().isOk())
				.andReturn();
	}
	
}
