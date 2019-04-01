package com.employee.management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.management.model.Department;
import com.employee.management.repository.DepartmentRepository;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;


	public List<Department> getAllDepartments(){
		List<Department> depts = (List<Department>)departmentRepository.findAll(); 
		return depts;
	}


	public Optional<Department> getDepartment(int id){ return
			departmentRepository.findById(id);
	}


	public void addDepartment(Department dept) {
		departmentRepository.save(dept);
	}

}
