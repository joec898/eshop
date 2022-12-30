package com.jctech.eshop.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jctech.eshop.exception.ObjectNotFoundException;
import com.jctech.eshop.model.Employee;
import com.jctech.eshop.model.EmployeeResponse;
import com.jctech.eshop.model.response.OperationResponse;
import com.jctech.eshop.model.response.OperationResponse.ResponseStatusEnum;
import com.jctech.eshop.service.EmployeeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = {"Employees"})
@RestController
@RequestMapping("/api")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/allemployees")
	public ResponseEntity<List<Employee>> getAllEmployees() {
		List<Employee> employees = (List<Employee>) employeeService.getAllEmployees();
		return new ResponseEntity<>(employees, HttpStatus.OK);
	}
	
	@GetMapping("/employees")
	public EmployeeResponse getEmpployeesByPage(
		    @ApiParam(value = ""    )               
		    @RequestParam(value = "page", defaultValue="1", required = false) Integer page,
		    @ApiParam(value = "between 1 to 1000" ) 
		    @RequestParam(value = "size", defaultValue="20", required = false) Integer size,
		    @RequestParam(value = "employeeid"  , required = false) Integer employeeId, 
		    Pageable pageable
	) {
		EmployeeResponse resp = new EmployeeResponse(); 
		Page<Employee> pg = employeeService.getEmployeesByPage(employeeId, pageable);
		resp.setPageStats(pg, true);
		resp.setItems(pg.getContent());
		return resp;
	}
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
		Employee cust = employeeService.findEmployeeById(Integer.valueOf(id));
		return new ResponseEntity<>(cust, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Add new Employee", response = OperationResponse.class)
	@PostMapping("/employees")
	public OperationResponse addNewEmployee(@RequestBody Employee Employee, HttpServletRequest req) {
		OperationResponse resp = new OperationResponse();
		if (this.employeeService.existsById(Employee.getId())) {
			resp.setOperationMessage("Unable to add Employee -- Employee already exists.");
			resp.setOperationStatus(ResponseStatusEnum.ERROR);
		} else {
			this.employeeService.saveOrUpdateEmployee(Employee);
			resp.setOperationMessage("Employee Added");
			resp.setOperationStatus(ResponseStatusEnum.SUCCESS);
		}
		return resp;
	} 
	
	@ApiOperation(value = "Update a Employee", response = OperationResponse.class)
	@PutMapping("/employees")
	public OperationResponse updateEmployee(@RequestBody Employee Employee, HttpServletRequest req) {
		OperationResponse resp = new OperationResponse();
		if (this.employeeService.existsById(Employee.getId())) {
			this.employeeService.saveOrUpdateEmployee(Employee);
			resp.setOperationMessage("Employee Updated");
			resp.setOperationStatus(ResponseStatusEnum.SUCCESS);
		} else {
			resp.setOperationMessage("Unable to update Employee -- Employee does not exist.");
			resp.setOperationStatus(ResponseStatusEnum.ERROR);
		}
		return resp;
	}
	
	@ApiOperation(value = "Delete a Employee", response = OperationResponse.class)
	@DeleteMapping("employees/{id}") 
	public OperationResponse deleteEmployeeById(@PathVariable Integer id, HttpServletRequest req) {
		OperationResponse resp = new OperationResponse();
		if (this.employeeService.existsById(id) ){
            this.employeeService.deleteEmployeeById(id);
            resp.setOperationStatus(ResponseStatusEnum.SUCCESS);
            resp.setOperationMessage("Employee Deleted");
        }
        else{
            resp.setOperationStatus(ResponseStatusEnum.ERROR);
            resp.setOperationMessage("No Employee Exist");
        }
		
		return resp;
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	private void ObjectNotFoundHandler(ObjectNotFoundException ex) {
		System.out.println("Employee not found");
	}
}
