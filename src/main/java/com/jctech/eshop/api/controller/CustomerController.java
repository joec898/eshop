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
import com.jctech.eshop.model.Customer;
import com.jctech.eshop.model.CustomerResponse; 
import com.jctech.eshop.model.response.OperationResponse;
import com.jctech.eshop.model.response.OperationResponse.ResponseStatusEnum; 
import com.jctech.eshop.service.CustomerService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

//@Transactional
@RestController
@RequestMapping("/api")
public class CustomerController {

	// private JdbcTemplate jdbcTermplate;

    @Autowired
	private CustomerService custService;
	
    @ApiOperation(value = "List of customers", response = CustomerResponse.class)
	@GetMapping("/customers")
	public CustomerResponse getCustomersByPage(
		    @ApiParam(value = ""    )               
		    @RequestParam(value = "page", defaultValue="1", required = false) Integer page,
		    @ApiParam(value = "between 1 to 1000" ) 
		    @RequestParam(value = "size", defaultValue="20", required = false) Integer size,
		    @RequestParam(value = "customerid"  , required = false) Integer customerId,
		    @RequestParam(value = "company"  , required = false) String company,
		    @RequestParam(value = "country"  , required = false) String country,
		    Pageable pageable
	) {
		CustomerResponse resp = new CustomerResponse(); 
		Page<Customer> pg = custService.getCustomersByPage(customerId, company, country, pageable);
		resp.setPageStats(pg, true);
		resp.setItems(pg.getContent());
		return resp;
	}

	@GetMapping("/allcustomers")
	public ResponseEntity<List<Customer>> getAllCustomers() {
		List<Customer> custs = (List<Customer>) custService.getAll();
		return new ResponseEntity<>(custs, HttpStatus.OK);
	}

	@GetMapping("/customers/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable String id) {
		Customer cust = custService.findCustomerById(Integer.valueOf(id));
		return new ResponseEntity<>(cust, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Add new customer", response = OperationResponse.class)
	@PostMapping("/customers")
	public OperationResponse addNewCustomer(@RequestBody Customer customer, HttpServletRequest req) {
		OperationResponse resp = new OperationResponse();
		if (this.custService.existsById(customer.getId())) {
			resp.setOperationMessage("Unable to add Customer -- Customer already exists.");
			resp.setOperationStatus(ResponseStatusEnum.ERROR);
		} else {
			this.custService.saveOrUpdateCustomer(customer);
			resp.setOperationMessage("Customer Added");
			resp.setOperationStatus(ResponseStatusEnum.SUCCESS);
		}
		return resp;
	} 
	
	@ApiOperation(value = "Update a customer", response = OperationResponse.class)
	@PutMapping("/customers")
	public OperationResponse updateCustomer(@RequestBody Customer customer, HttpServletRequest req) {
		OperationResponse resp = new OperationResponse();
		if (this.custService.existsById(customer.getId())) {
			this.custService.saveOrUpdateCustomer(customer);
			resp.setOperationMessage("Customer Updated");
			resp.setOperationStatus(ResponseStatusEnum.SUCCESS);
		} else {
			resp.setOperationMessage("Unable to update Customer -- Customer does not exist.");
			resp.setOperationStatus(ResponseStatusEnum.ERROR);
		}
		return resp;
	}
	
	@ApiOperation(value = "Delete a customer", response = OperationResponse.class)
	@DeleteMapping("customers/{id}") 
	public OperationResponse deleteCustomerById(@PathVariable Integer id, HttpServletRequest req) {
		OperationResponse resp = new OperationResponse();
		if (this.custService.existsById(id) ){
            this.custService.deleteCustomerById(id);
            resp.setOperationStatus(ResponseStatusEnum.SUCCESS);
            resp.setOperationMessage("Customer Deleted");
        }
        else{
            resp.setOperationStatus(ResponseStatusEnum.ERROR);
            resp.setOperationMessage("No Customer Exist");
        }
		
		return resp;
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	private void ObjectNotFoundHandler(ObjectNotFoundException ex) {
		System.out.println("Customer not found");
	}
	

}
