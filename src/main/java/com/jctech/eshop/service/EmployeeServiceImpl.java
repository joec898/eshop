package com.jctech.eshop.service;

import java.util.List;

 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jctech.eshop.exception.ObjectNotFoundException;
import com.jctech.eshop.model.Employee;
import com.jctech.eshop.repo.EmployeeRepo;

@Service
public class EmployeeServiceImpl implements EmployeeService {
 
	EmployeeRepo employeeRepo;
	
	@Autowired
	public EmployeeServiceImpl (EmployeeRepo repo) {
		employeeRepo = repo;
	}
	
	@Override
	public Employee findEmployeeById(Integer id) {
		Employee emp = employeeRepo.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Employee by id " + id + " was not found"));
 
		return emp;
	}
	
	@Override
	@Cacheable("Employees")
	public List<Employee> getAllEmployees() { 
		return employeeRepo.findAll();
	}
	
	@Override
	public Page<Employee> getEmployeesByPage(Integer employeeId, Pageable pageable) {

		Employee qry = new Employee();
		if (employeeId != null) { qry.setId(employeeId); } 
		
	    Page<Employee> pg = employeeRepo.findAll(Example.of(qry), pageable);
		return pg;
	}

	@CacheEvict(value = "Employees", allEntries = true)
	@Override
	public Employee saveOrUpdateEmployee(Employee employee) {
		Employee emp = employeeRepo.save(employee);
		return emp;
	} 

	@Override
	public void deleteEmployeeById(Integer id) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean existsById(Integer id) {
		return employeeRepo.existsById(id);
	}

}
