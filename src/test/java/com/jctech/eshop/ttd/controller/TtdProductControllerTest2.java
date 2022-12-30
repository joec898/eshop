package com.jctech.eshop.ttd.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock; 
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
 
import org.springframework.http.MediaType; 
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jctech.eshop.api.controller.ProductController;
import com.jctech.eshop.exception.ObjectNotFoundException;
import com.jctech.eshop.model.Product;
import com.jctech.eshop.service.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TtdProductControllerTest2 {

	String url = "/api/products/601";
	String putUrl = "/api/products";

	@Mock
	private ProductServiceImpl prodService;
	private Product product;

	@InjectMocks
	private ProductController productController;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		product = new Product(601, "P0", "Fisher Bike 100", 200L, 250L);
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
	}

	@AfterEach
	void tearDown() {
		product = null;
	}

	@Test
	public void getProduct_returnProductTest() throws Exception {
		given(prodService.findProductById(601)).willReturn(product);
		mockMvc.perform(MockMvcRequestBuilders.get(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(product)))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isMap())
				.andExpect(jsonPath("productCode").value(product.getProductCode()))
				.andExpect(jsonPath("productName").value(product.getProductName()));

		// verify(prodService).getAllProducts();
		// verify(prodService,times(1)).getAllProducts();
	}

	@Test
	public void getProduct_returnNotFoundTest() throws Exception {
		given(prodService.findProductById(601)).willThrow(new ObjectNotFoundException());

		mockMvc.perform(MockMvcRequestBuilders.get(url)).andExpect(status().isNotFound());
	}

	@Test
	public void getProduct_shouldReturnRespectiveProducct() throws Exception {
		when(prodService.findProductById(601)).thenReturn(product);
		mockMvc.perform(
				MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON).content(asJsonString(product)))
				.andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void postUrlWithProduct_whenSave_returnSavedMessage() throws Exception {
		given(prodService.existsById(product.getId())).willReturn(false);
		when(prodService.saveOrUpdateProduct(product)).thenReturn(product);
		mockMvc.perform(MockMvcRequestBuilders.post(putUrl)
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(product)))
		        .andDo(MockMvcResultHandlers.print())
		        .andExpect(status().isOk())
				.andExpect(jsonPath("$").isMap())
				.andExpect(jsonPath("operationMessage").value("Product Added"));

		verify(prodService, times(1)).saveOrUpdateProduct(product);
	}

	@Test
	public void putUrlWithProduct_whenUpdate_returnUpdatedMessage() throws Exception {
		given(prodService.existsById(product.getId())).willReturn(true);
		when(prodService.saveOrUpdateProduct(product)).thenReturn(product);
		mockMvc.perform(MockMvcRequestBuilders.put(putUrl)
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(product)))
		        .andDo(MockMvcResultHandlers.print())
		        .andExpect(status().isOk())
				.andExpect(jsonPath("$").isMap())
				.andExpect(jsonPath("operationMessage").value("Product Updated"));

		verify(prodService, times(1)).saveOrUpdateProduct(product);
	}
	
	public void deleteUrlAndId_whenDelete_returnDeletedMessage() throws Exception {
		given(prodService.existsById(product.getId())).willReturn(true);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(url)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "Product Deleted");

		verify(prodService, times(1)).deleteProductById(product.getId());
	}

	@Test
	public void deleteUrlAndId_thenReturnNothing() throws Exception {
		given(prodService.existsById(product.getId())).willReturn(true);
		doNothing().when(prodService).deleteProductById(product.getId());

		mockMvc.perform(MockMvcRequestBuilders.delete(url));

		verify(prodService, times(1)).deleteProductById(product.getId());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
