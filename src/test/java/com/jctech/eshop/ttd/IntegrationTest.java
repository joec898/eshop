package com.jctech.eshop.ttd;
 
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest; 
import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.data.mongodb.core.CollectionOptions;
//import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.jctech.eshop.model.Customer;
import com.jctech.eshop.repo.CustomerRepository;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties="")
//@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {
	
	@Autowired
	private WebTestClient webTestClient;
	
	@Autowired
	private CustomerRepository custRepo;
	
	/**
	private ReactiveMongoOperations operations;
	
	@Before
	public void setUp() throws Exception{
		CollectionOptions options = CollectionOptions.empty()
				.size(1024 * 1024)
				.maxDocuments(100)
				.capped();
		this.operations.createCollection(Customer.class),  options)
		.then()
		.block();
		
		this.operations.save(new Customer(1,"Smith","Jack","jack@mail.com","3M","2223334444"))
		.then()
		.block();
	}
	**/
		
	//@Test
	public void getCustomer_withName_returnCustomer() {
		Customer cust = this.webTestClient.get().uri("/api/customer/{id}", 2)
				.exchange().expectStatus().isOk()
				.expectBody(Customer.class).returnResult().getResponseBody();
		
		assertEquals(cust.getFirstName(), "Emily"); 
	}
	
}
