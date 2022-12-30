package com.jctech.eshop.ttd.controller;

 
import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.jctech.eshop.api.controller.CustomerController;
import com.jctech.eshop.exception.ObjectNotFoundException;
import com.jctech.eshop.model.Customer;
import com.jctech.eshop.service.CustomerService; 

/**
 * @author jc
 */

@ExtendWith(MockitoExtension.class)
//@WebMvcTest(CustomerController.class)
public class TtdCustomerControllerTest2 {
	 
	String url = "/api/customers/0";
		
	Customer customer;

	@Mock
	private CustomerService custService;
	
	@InjectMocks
	CustomerController customerController;
	
	@Autowired
    private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		customer = new Customer(1,"Smith","Jack","jack@mail.com","3M","2223334444");
		mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
	}
	
	@Test
	public void getCustomer_ShouldReturnCustomer() throws Exception {
		given(custService.findCustomerById(0)).willReturn(customer);

		mockMvc.perform(MockMvcRequestBuilders.get(url))
		.andExpect(status().isOk())
		.andExpect(jsonPath("firstName").value("Jack"))
		.andExpect(jsonPath("company").value("3M"));
	}
	
	@Test
	public void getCustomer_notFound() throws Exception {
		given(custService.findCustomerById(0)).willThrow(new ObjectNotFoundException()); 
		
		mockMvc.perform(MockMvcRequestBuilders.get(url))
		.andExpect(status().isNotFound());
	}
}
