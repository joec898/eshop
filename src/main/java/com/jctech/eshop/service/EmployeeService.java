package com.jctech.eshop.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jctech.eshop.model.Employee;

public interface EmployeeService {
	Employee findEmployeeById(Integer id);

	List<Employee> getAllEmployees();

	Page<Employee> getEmployeesByPage(Integer customerId, Pageable pageable);

	Employee saveOrUpdateEmployee(Employee employee);

	void deleteEmployeeById(Integer id);

	boolean existsById(Integer id);
}
