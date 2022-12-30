package com.jctech.eshop.ttd;

import org.assertj.core.api.Assertions;
//import reactor.core.CorePublisher;
//import reactor.core.publisher.Flux;
//import reactor.test.StepVerifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.jctech.eshop.model.Customer;
import com.jctech.eshop.repo.CustomerRepo;
import com.jctech.eshop.repo.CustomerRepository;

//@RunWith(SpringRunner.class)
@DataMongoTest
public class TtdCustomerRepositoryTest {

	@Autowired
	private CustomerRepository custRepo;

	/**
	@Before
	public void setUp() throws Exception{
		this.custRepo.save(new Customer(1,"Smith","Jack","jack@mail.com","3M","2223334444"))
		.then()
		.block();
	}
	
	@After
	public void tearDown() throws Exception {
		this.custRepo.deleteAll()
		.then()
		.block();
	}

	@Test
	public void getCustomer_returnsCustomer() throws Exception {
		StepVerifier.create(custRepo.findByFirstName("Jack"))
		.consumeNextWith(cust -> {
			assertThat(cust.getFirstName().isEqualTo("Jack"))
		})
		.verifyComplete();
	}
**/
 
}
