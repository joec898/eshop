package com.jctech.eshop.ttd;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.jctech.eshop.model.Product;
import com.jctech.eshop.repo.ProductRepo;
import com.jctech.eshop.service.ProductService;

//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
public class ProductCatchingTest {

	@MockBean
	private ProductRepo prodRepo;
	
	@Autowired
	private ProductService prodService;
	
	//@Test
	public void productCaching() {
		Product prod = new Product(600,"P0","Fisher Bike 100", 200L, 250L);
		given(prodRepo.findByProductCode("P0")).willReturn(prod);
		
		Product product = prodService.findProductByCode("P0");
		
		assertNotNull(product);
		
		verify(prodRepo, times(1)).findByProductCode("P0");
	}
	
	//@Test
	public void productsCaching() {
		List<Product> prods = new ArrayList<>();
		prods.add(new Product(600,"P0","Fisher Bike 100", 200L, 250L));
		prods.add(new Product(601,"P1","Fisher Bike 120", 220L, 280L));
		prods.add(new Product(602,"P2","Fisher Bike 140", 230L, 295L));
		 
		given(prodRepo.findAll()).willReturn(prods);
		
		List<Product> prodList = prodService.getAllProducts();
		
		verify(prodRepo, Mockito.times(1)).findAll();
		
		List<Product> retryProds = prodService.getAllProducts();
		
		verifyNoMoreInteractions(prodRepo); 
	}
}
