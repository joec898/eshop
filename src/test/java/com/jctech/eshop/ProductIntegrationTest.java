package com.jctech.eshop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.http.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jctech.eshop.model.Product;

/**
 * @author jc
 */

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ProductIntegrationTest extends IntegrationTestBase { 	 

	private Product product;
	private String token;
	
	@BeforeEach
	public void setUp() {
		token = getSessionToken(this.username, this.password);
		headers.add("Authorization", "Bearer " + token);
		
		product = new Product(621, "P0", "Fisher Bike 100", 200L, 250L);
	}
	
	@Test
	public void getProduct_returnProduct() throws Exception {

		baseUrl += port + "/api/products/601";
		ResponseEntity<Product> resp = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, Product.class);

		assertEquals(HttpStatus.OK, resp.getStatusCode());
		//assertEquals("P1", resp.getBody().getProductCode());
	}

	@Test
	public void getProduct_notFound() throws Exception {

		baseUrl += port + "/api/products/0";
		ResponseEntity<Product> resp = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, Product.class);

		assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
	}
	
	@Test
	public void getProductsPage_returnProductsPage() throws Exception {

		//baseUrl += port + "/api/products?page=0&size=4"; 
		int pageSize = 4;
		int totalPage = 5;
		for (int page = 0; page < totalPage; page++) {
			
			String url = String.format("%s%s/api/products?page=%s&size=%s", baseUrl, port, page, pageSize);

			ResponseEntity<?> resp = restTemplate.exchange(url, HttpMethod.GET,	entity, String.class);

			JsonNode node = new ObjectMapper().readTree((String) resp.getBody());
			
			assertEquals(HttpStatus.OK, resp.getStatusCode());
			assertEquals(pageSize, node.path("items").size());
		}
	} 
	
	@Test
	public void givenPostUrl_whenSaved_returnCreatedMessage() throws Exception { 
		baseUrl += port + "/api/products";  
		headers.set("X-COM-PERSIST", "true"); 
		
		HttpEntity<Product> request = new HttpEntity<>(product, headers);

		ResponseEntity<String> resp = restTemplate.postForEntity(baseUrl,  request, String.class);

		assertEquals(HttpStatus.OK, resp.getStatusCode());
	}
	
	@Test
	public void givenDeleteUrl_whenDelete_returnDeletedMessage() throws Exception { 
		baseUrl += port + "/api/products/601"; 
		 
		ResponseEntity<String> resp = restTemplate.exchange(baseUrl, HttpMethod.DELETE, entity, String.class);

		assertEquals(HttpStatus.OK, resp.getStatusCode());
	}
	 
}
