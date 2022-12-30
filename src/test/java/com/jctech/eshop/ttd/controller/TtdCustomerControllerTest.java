package com.jctech.eshop.ttd.controller;

 
import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.jctech.eshop.api.controller.CustomerController;
import com.jctech.eshop.exception.ObjectNotFoundException;
import com.jctech.eshop.model.Customer;
import com.jctech.eshop.service.CustomerService; 

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
public class TtdCustomerControllerTest {
	 
	String url = "/api/customers/0";
		
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private CustomerService custService;
	
	//@Test
	public void getCustomer_ShouldReturnCustomer() throws Exception {
		given(custService.findCustomerById(0))
            .willReturn(new Customer(1,"Smith","Jack","jack@mail.com","3M","2223334444"));

		mockMvc.perform(MockMvcRequestBuilders.get(url))
		.andExpect(status().isOk())
		.andExpect(jsonPath("firstName").value("Jack"))
		.andExpect(jsonPath("company").value("3M"));
	}
	
	//@Test
	public void getCustomer_notFound() throws Exception {
		given(custService.findCustomerById(0)).willThrow(new ObjectNotFoundException()); 
		
		mockMvc.perform(MockMvcRequestBuilders.get(url))
		.andExpect(status().isNotFound());
	}
}
