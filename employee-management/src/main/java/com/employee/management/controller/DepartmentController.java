package com.employee.management.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.management.exception.ErrorResponse;
import com.employee.management.exception.RecordNotFoundException;
import com.employee.management.model.Department;
import com.employee.management.service.DepartmentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/api/v1")
@Api(value="Department Management Controller APIs")


@RestController
public class DepartmentController {

	static final Logger logger  = LogManager.getLogger(DepartmentController.class.getName());

	@Autowired
	private DepartmentService departmentService;

	/*
	 * Displaying All Available Departments
	 */
	@GetMapping("/departments")
	@ApiOperation("Get all available departments")
	public List<Department> getAllDepartment() throws RecordNotFoundException{
		logger.info("Received API to fetch all departments " );
		List<Department> department =  departmentService.getAllDepartments();
		if(department.size() == 0) {
			throw new RecordNotFoundException("No Departments present. Please add the departments ");
		}
		return department;
	}

	/*
	 * Displaying Department by ID
	 */

	@GetMapping("/departments/{id}")

	@ApiOperation("Get department by Id") public ResponseEntity<Object>
	getDepartment(@PathVariable int id) throws RecordNotFoundException {

		logger.info("Received ID for department " +id); 

		Department department = departmentService.getDepartment(id)
				.orElseThrow(() -> new RecordNotFoundException("Department is not Found. Please Check the department id again : " + id));

		return new ResponseEntity<Object>(department, HttpStatus.OK); }

	/*
	 * Adding Departments
	 */
	@PostMapping("/departments")
	@ApiOperation("Add department")
	public ResponseEntity<Object> addDepartment(@RequestBody Department department){
		try {
			logger.info("Received request-body for department " +department);
			departmentService.addDepartment(department);
		} catch (DataIntegrityViolationException e) {
			ErrorResponse error = new ErrorResponse("Dpartment Id already exists ! Please check ! ");
			return new ResponseEntity<Object>(error, HttpStatus.CONFLICT);
		} catch (Exception e) {
			ErrorResponse error = new ErrorResponse("Entry Updation Failed. Server may be down. Try after sometime");
			return new ResponseEntity<Object>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Object>("Entry done successfully ", HttpStatus.CREATED);

	}
}

