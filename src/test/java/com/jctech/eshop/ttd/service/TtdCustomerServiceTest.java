package com.jctech.eshop.ttd.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith; 

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jctech.eshop.exception.ObjectNotFoundException;
import com.jctech.eshop.model.Customer;
import com.jctech.eshop.repo.CustomerRepo;
import com.jctech.eshop.service.CustomerServiceImpl;

/**
 * @author jc
 */

// JUnit 4 test
//@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class TtdCustomerServiceTest {

	@Mock
	private CustomerRepo custRepo;

	private CustomerServiceImpl custService;

	@BeforeEach
	public void setUp() throws Exception {
		custService = new CustomerServiceImpl(custRepo);
	}
	
	@AfterEach
	public void tearDown() {
		custService = null;
	}

	@DisplayName("JUnit test service for get customer by id operation")
	@Test
	public void givenCustomer_whenGetById_returnCustomer() {
		Customer cust = new Customer(109, "Smith", "Jack", "jack@mail.com", "3M", "2223334444");
		given(custRepo.findById(1)).willReturn(Optional.of(cust));

		Customer customer = custService.findCustomerById(1);

		Assertions.assertThat(customer.getFirstName()).isEqualTo(cust.getFirstName());
	}

	@DisplayName("JUnit test service for get customer by invalid id operation")
	@Test
	public void givenInvalidId_whenGetById_throwsException() {
		given(custRepo.findById(0)).willThrow(new ObjectNotFoundException());
//		Customer customer = custService.findCustomerById(0);
//		Assertions.assertThat(customer).isNull();
		assertThrows(ObjectNotFoundException.class, () -> custService.findCustomerById(0));
	}

}
