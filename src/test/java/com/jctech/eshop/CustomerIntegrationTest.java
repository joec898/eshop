package com.jctech.eshop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jctech.eshop.model.Customer;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerIntegrationTest extends IntegrationTestBase {

	private Customer customer;
	private String token;

	@BeforeEach
	public void setUp() {
		token = getSessionToken(this.username, this.password);

		headers.add("Authorization", "Bearer " + token);

		customer = new Customer(109, "Smith", "Jack", "jack@mail.com", "3M", "2223334444", "3688 Pioneer Trail", "",
				"Minneaplis", "Minnesota", "55343", "United States");
	}

	@DisplayName("JUnit test api for get customer by id operation")
	@Test
	public void givenUrl_whenGET_returnCustomer() throws Exception {

		baseUrl += port + "/api/customers/2";

		ResponseEntity<Customer> resp = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, Customer.class);

		assertEquals(HttpStatus.OK, resp.getStatusCode());
		assertEquals("Emily", resp.getBody().getFirstName());
	}

	@DisplayName("JUnit test api for get customer by url of invalid id operation")
	@Test
	public void givenInvalidUrlForCustomers_whenGET_returnNOT_FOUND() throws Exception {

		baseUrl += port + "/api/customers/0";

		ResponseEntity<Customer> resp = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, Customer.class);

		assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
	}

	@DisplayName("JUnit test for get all customers in pages operation")
	@Test
	public void givenPage_PageSize_whenFindAll_thenReturnCustomersInPage() throws Exception {

		// baseUrl += port + "/api/customers?page=0&size=4";
		int pageSize = 10;
		int totalPage = 3;
		for (int page = 0; page < totalPage; page++) {

			String url = String.format("%s%s/api/customers?page=%s&size=%s", baseUrl, port, page, pageSize);

			ResponseEntity<?> resp = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

			JsonNode node = new ObjectMapper().readTree((String) resp.getBody());

			assertEquals(HttpStatus.OK, resp.getStatusCode());
			assertEquals(pageSize, node.path("items").size());
		}
	}

	@DisplayName("JUnit test for add new customer operation")
	@Test
	public void givenPostUrl_whenSaved_returnCreatedMessage() throws Exception {
		baseUrl += port + "/api/customers";
		headers.set("X-COM-PERSIST", "true");

		HttpEntity<Customer> request = new HttpEntity<>(customer, headers);

		ResponseEntity<String> resp = restTemplate.postForEntity(baseUrl, request, String.class);

		assertEquals(HttpStatus.OK, resp.getStatusCode());
	}

	@DisplayName("JUnit test for delete customer operation")
	@Test
	public void givenDeleteUrl_whenDelete_returnDeletedMessage() throws Exception {
		baseUrl += port + "/api/customers/601";

		ResponseEntity<String> resp = restTemplate.exchange(baseUrl, HttpMethod.DELETE, entity, String.class);

		assertEquals(HttpStatus.OK, resp.getStatusCode());
	}

}
