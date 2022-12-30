package com.jctech.eshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.jctech.eshop.exception.ObjectNotFoundException;
import com.jctech.eshop.model.Customer;
import com.jctech.eshop.repo.CustomerRepo;

@Service
public class CustomerServiceImpl implements CustomerService {

	private CustomerRepo customerRepo;

	@Autowired
	public CustomerServiceImpl(CustomerRepo repo) {
		this.customerRepo = repo;
	}

	@Override
	@Cacheable("Customers")
	public List<Customer> getAll() {
		return (List<Customer>) customerRepo.findAll();
	}

	@Override
	public Page<Customer> getCustomersByPage(Integer customerId, String company, String country, Pageable pageable) {

		Customer qry = new Customer();
		if (customerId != null) { qry.setId(customerId); }
		if (company != null) { qry.setCompany(company); }
		if (country != null) { qry.setCountry(country); }
		
	    Page<Customer> pg = customerRepo.findAll(Example.of(qry), pageable);
		return pg;
	}
 
	@Override
	public Customer findCustomerById(Integer id) {
		Customer cust = customerRepo.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Customer by id " + id + " was not found"));

		return cust;
	}
 
	@Override
	public Customer findCustomerByFirstName(String firstName) {
		Customer cust = customerRepo.findByFirstName(firstName);
		if (cust == null) {
			throw new ObjectNotFoundException("Customer by firstName " + firstName + " was not found");
		}
		return cust;
	}
	
	@Override
	@CacheEvict(value = "Customers", allEntries = true)
	public void deleteCustomerById(Integer id) {
		customerRepo.deleteById(id); 
	}

	@Override
	@CacheEvict(value = "Customers", allEntries = true)
	public Customer saveOrUpdateCustomer(Customer customer) {
		return (Customer) customerRepo.save(customer);
	} 
	
	@Override
	public boolean existsById(Integer id) { 
		return customerRepo.existsById(id);
	}

}
