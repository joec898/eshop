package com.jctech.eshop.ttd.controller;


import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension; 
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.jctech.eshop.api.controller.ProductController;
import com.jctech.eshop.exception.ObjectNotFoundException;
import com.jctech.eshop.model.Product;
import com.jctech.eshop.service.ProductService;

@ExtendWith(SpringExtension.class) //Note: should use import org.junit.jupiter.api.Test
@WebMvcTest(ProductController.class)
public class TtdProductControllerTest {

	String url = "/api/products/0";
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	private ProductService prodService;	
	
	
	//@Test
	public void getProduct_returnProductTest() throws Exception {
		Product prod = new Product(0,"P0","Fisher Bike 100", 200L, 250L);
		given(prodService.findProductById(0)).willReturn(prod);
		
		mockMvc.perform(MockMvcRequestBuilders.get(url))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isMap())
				.andExpect(jsonPath("productCode").value("P0"))
				.andExpect(jsonPath("productName").value("Fisher Bike 100")); 
		
	}
	
	//@Test
	public void getProduct_returnNotFoundTest() throws Exception { 
		given(prodService.findProductById(0)).willThrow(new ObjectNotFoundException());
		
		mockMvc.perform(MockMvcRequestBuilders.get(url))
		.andExpect( status().isNotFound()); 
		
	}
}
