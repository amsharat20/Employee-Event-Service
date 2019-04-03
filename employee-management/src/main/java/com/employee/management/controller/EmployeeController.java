package com.employee.management.controller;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.management.exception.ErrorResponse;
import com.employee.management.exception.RecordNotFoundException;
import com.employee.management.kafkaservice.Event;
import com.employee.management.model.Department;
import com.employee.management.model.Employee;
import com.employee.management.repository.DepartmentRepository;
import com.employee.management.repository.EmployeeRepository;
import com.employee.management.service.EmployeeService;

import io.swagger.annotations.Api;

@RequestMapping("/api/v1")
@Api(value = "Employee Management APIs")

@RestController
public class EmployeeController {

	static final Logger logger = LogManager.getLogger(EmployeeController.class.getName());

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private DepartmentRepository departmentRepository;


	@Autowired
	KafkaTemplate<String, Event> kafkaTemplate;

	final static String KAFKA_TOPIC =  "employee-topic";

	/*
	 * Displaying All Available Departments
	 */
	@GetMapping("/employees")
	public List<Employee> getAllEmployee() throws RecordNotFoundException {
		logger.info("Received API to fetch all employees ");
		List<Employee> employee = employeeService.getAllEmployees();
		if (employee.size() == 0) {
			throw new RecordNotFoundException("No Employees present. Please add the employee details ");
		}
		return employee;
	}

	/*
	 * Displaying Employee By Id
	 */

	@GetMapping("/employees/{uuid}") public ResponseEntity<Object>
	getEmployee(@PathVariable UUID uuid) throws RecordNotFoundException {
		logger.info("Received UUID for Employee " + uuid);

		Employee employee = 	employeeService.getEmployee(uuid)
				.orElseThrow(() -> new RecordNotFoundException("Employee is not Found. Please Check the id again : " + uuid));

		return new ResponseEntity<Object>(employee, HttpStatus.OK);
	}

	/*
	 * Add Employee details
	 */
	@PostMapping("/employees")
	public ResponseEntity<Object> addEmployees(@RequestBody Employee employee) throws Exception {

		boolean checkEmail = validateEmail(employee.getEmail());
		if(!checkEmail) {
			throw new Exception("Email is not Valid");
		}

		boolean checkDept = validateDepartmentId(employee.getDeptid());
		logger.info("Department is present " + checkDept);

		try {
			logger.info("Received request-body for employee " + employee);

			employeeService.addEmployee(employee);

		} catch (DataIntegrityViolationException e) {
			ErrorResponse error = new ErrorResponse("Email ID already exists ! Please check ! ");
			return new ResponseEntity<Object>(error, HttpStatus.CONFLICT);
		} catch (Exception e) {
			ErrorResponse error = new ErrorResponse("Entry Updation Failed. Server may be down. Try after sometime");
			return new ResponseEntity<Object>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		kafkaTemplate.send(KAFKA_TOPIC, new Event("Event-Create",
				employee.getUuid(), employee.getFirstName() + "." +
						employee.getLastName(), employee.getEmail(), "Employee Created"));

		return new ResponseEntity<Object>("Entry done successfully ", HttpStatus.CREATED);
	}

	/*
	 * Update employee details by id
	 */

	@PutMapping("/employees/{uuid}") 
	public ResponseEntity<Object> updateEmployee(@RequestBody Employee updatedEmployee, @PathVariable UUID uuid)
			throws Exception{

		Employee employee = employeeRepository.findById(uuid)
				.orElseThrow(() -> new RecordNotFoundException("Employee Not Found : " + uuid));

		boolean checkEmail = validateEmail(updatedEmployee.getEmail());
		if(!checkEmail) {
			throw new Exception("Email is not Valid");
		}

		boolean checkDept = validateDepartmentId(updatedEmployee.getDeptid());

		employee.setBirthdate(updatedEmployee.getBirthdate());
		employee.setDeptid(updatedEmployee.getDeptid());
		employee.setEmail(updatedEmployee.getEmail());
		employee.setFirstName(updatedEmployee.getFirstName());
		employee.setLastName(updatedEmployee.getLastName()); 

		try {

			employeeService.updateEmployee(employee, uuid);

		} catch (DataIntegrityViolationException e) {
			ErrorResponse error = new ErrorResponse("Email ID already exists ! Please check ! ");
			return new ResponseEntity<Object>(error, HttpStatus.CONFLICT);
		} catch (Exception e) {
			ErrorResponse error = new ErrorResponse("Entry Updation Failed. Server may be down. Try after sometime");
			return new ResponseEntity<Object>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		kafkaTemplate.send(KAFKA_TOPIC,new Event("Event-Update",
				employee.getUuid(), employee.getFirstName()+ "."+
						employee.getLastName(), employee.getEmail(), "Employee Updated" )); 

		return new ResponseEntity<Object>(employee, HttpStatus.OK);
	}


	/*
	 * Delete all employees
	 */
	@DeleteMapping("/employees")
	public ResponseEntity<Object> deleteAllEmployees() {
		employeeService.deleteAllEmployees();
		return new ResponseEntity<Object>("All Employees deleted", HttpStatus.OK);
	}


	/*
	 * Delete employee by Id
	 */
	@DeleteMapping("employees/{uuid}") 
	public ResponseEntity<Object> deleteEmployeeByID(@PathVariable UUID uuid) throws RecordNotFoundException {

		Employee employee = employeeRepository.findById(uuid)
				.orElseThrow(() -> new RecordNotFoundException("Employee Not Found. Please check : " + uuid));

		employeeService.deleteEmployeeByID(uuid);

		kafkaTemplate.send(KAFKA_TOPIC,new Event("Event-Delete",employee.getUuid(), employee.getFirstName()+ "."+
				employee.getLastName(), employee.getEmail(), "Employee Deleted" ));

		return  new ResponseEntity<Object>("Employee deleted for ID" + uuid, HttpStatus.OK);
	}


	private boolean validateEmail(String emailCheck) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
				"[a-zA-Z0-9_+&*-]+)*@" + 
				"(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
				"A-Z]{2,7}$"; 
		Pattern pat = Pattern.compile(emailRegex); 
		if (emailCheck == null) {
			return false; 
		} else {
			return pat.matcher(emailCheck).matches(); 
		}
	}


	private boolean validateDepartmentId(int deptid) throws	RecordNotFoundException { 
		logger.info("Inside Validate dept");
		Department department = 	departmentRepository.findById(deptid)
				.orElseThrow(() -> new RecordNotFoundException("Department Not Found. Please check : " + deptid));
		return true; 
	}
}


