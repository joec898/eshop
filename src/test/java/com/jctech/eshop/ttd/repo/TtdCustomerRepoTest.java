package com.jctech.eshop.ttd.repo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import com.jctech.eshop.model.Customer;
import com.jctech.eshop.repo.CustomerRepo;

/**
 * @author jc
 */

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // https://howtodoinjava.com/spring-boot2/testing/datajpatest-annotation/
@ActiveProfiles("test")
public class TtdCustomerRepoTest {

	private Customer customer;

	@Autowired
	private CustomerRepo custRepo;

	@Autowired
	private TestEntityManager entityManager;

	@BeforeEach
	public void setUp() {
		customer = new Customer(109, "Smith", "Jack", "jack@mail.com", "3M", "2223334444", "3688 Pioneer Trail", "",
				"Minneaplis", "Minnesota", "55343", "United States");
	}
	
	@AfterEach
	public void tearDown() {
		customer = null;
	}

	@DisplayName("JUnit test for get customer by id operation")
	@Test
	public void givenCustomer_whenFindById_thenReturnCustomer() throws Exception {

		Customer custSaved = entityManager.persistFlushFind(customer);
		// Customer custSaved = custRepo.save(customer);
		Customer cust = custRepo.findById(custSaved.getId()).get();
        custRepo.delete(custSaved);
        
		assertThat(cust.getFirstName()).isEqualTo(custSaved.getFirstName());
	}

	@DisplayName("JUnit test for get customer by firstName operation")
	@Test
	public void givenCustomer_whenFindByFirstName_thenReturnCustomer() throws Exception {

		// Customer savedCust = entityManager.persistFlushFind(customer);
		Customer custSaved = custRepo.save(customer);
		Customer cust = custRepo.findByFirstName(custSaved.getFirstName());
		custRepo.delete(custSaved);
		
		assertThat(cust.getFirstName()).isEqualTo(custSaved.getFirstName());
	} 
	
	@DisplayName("JUnit test for get all customers operation")
	@Test
	public void whenFindAll_thenReturnAllCustomers() throws Exception {
		List<Customer> result = new ArrayList<>();
		custRepo.findAll().forEach(e -> result.add(e)); 
		System.out.println(">>>>>>>>>>>>> number of customers retrieved: " + result.size());
		assertTrue(result.size() > 1);
	} 

	@DisplayName("JUnit test for get all customers in pages operation")
	@Test
	public void givenPage_PageSize_whenFindAll_thenReturnCustomersInPage() {
		// Pageable p = Pageable.ofSize(10);
		int page = 0;
		int size = 10;
		Pageable p = PageRequest.of(page, size);
		Customer qry = new Customer();
		Page<Customer> pg = custRepo.findAll(Example.of(qry), p);

		List<Customer> custs = pg.getContent();
		assertThat(custs.size()).isEqualTo(10);
	}

	@DisplayName("JUnit test for save customer operation")
	@Test
	public void givenCustomer_whenSave_theReturnSavedCustomer() {
		Customer custSaved = custRepo.save(customer);
		custRepo.delete(custSaved);
		
		assertThat(custSaved).isNotNull();
		assertThat(customer.getEmail()).isEqualTo(custSaved.getEmail());
	}

	@DisplayName("JUnit test for delete customer operation")
	@Test
	public void givenCustomer_whenDelete_thenRemoveCustomer() {
		Customer custSaved = custRepo.save(customer);

		custRepo.deleteById(custSaved.getId());
		Optional<Customer> optionalCust = custRepo.findById(custSaved.getId());
		assertThat(optionalCust).isEmpty();
	}
}
