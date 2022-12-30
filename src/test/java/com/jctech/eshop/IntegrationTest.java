package com.jctech.eshop;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest; 
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jctech.eshop.model.Customer;

/**
 * @author jc
 */

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties="")
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest extends IntegrationTestBase {
	
	@Autowired
	private TestRestTemplate restTemplate;
	private String token;
	
	@BeforeEach
	public void setUp() {
		token = getSessionToken(this.username, this.password);
		headers.add("Authorization", "Bearer " + token);
	}
	
	//@Test
	public void testFirst() {
		ResponseEntity<Customer[]> resp =
		    restTemplate.getForEntity("/api/allcustomers", Customer[].class, entity); 
		
		assertEquals(resp.getStatusCode(), HttpStatus.OK);
		assertTrue(((Customer[])resp.getBody()).length > 0);
	}
	
	@Test
	public void testFirst2() { 
		ResponseEntity<List<Customer>> resp = restTemplate.exchange("/api/allcustomers",
			    HttpMethod.GET,entity, new ParameterizedTypeReference<List<Customer>>() {});
		
		assertEquals(resp.getStatusCode(), HttpStatus.OK);
		assertTrue(((List<Customer>)resp.getBody()).size() > 0);
	}
	
}
 