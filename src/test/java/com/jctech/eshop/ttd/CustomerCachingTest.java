 package com.jctech.eshop.ttd;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jctech.eshop.model.Customer;
import com.jctech.eshop.repo.CustomerRepo;
import com.jctech.eshop.service.CustomerService;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE )
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@AutoConfigureCache
public class CustomerCachingTest {

	@Autowired
	private CustomerService custService;
	
	@MockBean
	private CustomerRepo custRepo;
	
	//@Test
	public void caching() throws Exception {
		given(custRepo.findByFirstName(anyString()))
           .willReturn(new Customer(109,"Smith","Jack","jack@mail.com","3M","2223334444"));
	
		custService.findCustomerByFirstName("Jack");
		custService.findCustomerByFirstName("Jack");
		
		verify(custRepo, times(1)).findByFirstName("Jack");
	}
	
	
}
