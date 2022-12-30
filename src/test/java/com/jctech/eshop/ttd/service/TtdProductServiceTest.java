package com.jctech.eshop.ttd.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jctech.eshop.exception.ObjectNotFoundException;

import com.jctech.eshop.model.Product;
import com.jctech.eshop.repo.ProductRepo; 
import com.jctech.eshop.service.ProductServiceImpl;

// JUnit 5S
@ExtendWith(MockitoExtension.class)
public class TtdProductServiceTest {

	@Mock
	ProductRepo prodRepo;

	@InjectMocks
	ProductServiceImpl prodService;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@DisplayName("JUnit test service for get product by id operation")
	@Test
	public void givenProduct_whenFindByProductId_returnProduct() throws Exception {
		Product prod = new Product(0, "P0", "Fisher Bike 100", 200L, 250L);
		given(prodRepo.findById(0)).willReturn(Optional.of(prod));

		Product p = prodService.findProductById(0);

		assertNotNull(p);
		assertThat(p.getProductCode()).isEqualTo(prod.getProductCode());
	}

	@DisplayName("JUnit test service for get product by invalid id operation")
	@Test
	public void givenInvalidProductId_whenFindById_throwsException() throws Exception {
		given(prodRepo.findById(0)).willThrow(new ObjectNotFoundException());

		assertThrows(ObjectNotFoundException.class, () -> prodService.findProductById(0));
	}

}
