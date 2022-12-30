package com.jctech.eshop.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jctech.eshop.model.Customer;

public interface CustomerService {

	List<Customer> getAll();

	Page<Customer> getCustomersByPage(Integer customerId, String company, String country, Pageable pageable);

	Customer findCustomerById(Integer id);

	Customer findCustomerByFirstName(String firstName);

	void deleteCustomerById(Integer id);

	Customer saveOrUpdateCustomer(Customer customer);

	boolean existsById(Integer id);

}