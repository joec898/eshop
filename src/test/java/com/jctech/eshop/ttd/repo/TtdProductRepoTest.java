package com.jctech.eshop.ttd.repo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import com.jctech.eshop.model.Product;
import com.jctech.eshop.repo.ProductRepo;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // https://howtodoinjava.com/spring-boot2/testing/datajpatest-annotation/
@ActiveProfiles("test")
public class TtdProductRepoTest {

	private Product product;

	@Autowired
	private ProductRepo prodRepo;

	@BeforeEach
	public void setUp() {
		product = new Product(600, "P0", "Nikon D830", "DESC", 1167L, 1123L, 75, 10, 10, 50, 1, "Camera");
	}

	@AfterEach
	public void tearDown() {
		product = null;
	}

	@DisplayName("JUnit test for get product by id operation")
	@Test
	public void givenProduct_whenFindById_returnProduct() {
		Product prodSaved = prodRepo.save(product);
		Optional<Product> prod = prodRepo.findById(prodSaved.getId());
		prodRepo.delete(prodSaved);
		
		assertTrue(prod.isPresent());
	}

	@DisplayName("JUnit test for get product by invalid id operation")
	@Test
	public void getProduct_notFound() {
		Optional<Product> prod = prodRepo.findById(0);
		assertFalse(prod.isPresent());
	}

	@DisplayName("JUnit test for get all products operation")
	@Test
	public void whenFindAll_thenReturnAllProducts() throws Exception {
		List<Product> result = new ArrayList<>();
		prodRepo.findAll().forEach(e -> result.add(e));
		System.out.println(">>>>>>>>> number of products retrieved: " + result.size());
		assertTrue(result.size() > 1);
	}
	
	
	@DisplayName("JUnit test for get all products in pages operation")
	@Test
	public void givenPage_PageSize_whenFindAll_thenReturnProductsInPage() {
		// Pageable p = Pageable.ofSize(10);
		int page = 0;
		int size = 10;
		Pageable p = PageRequest.of(page, size);
		Product qry = new Product();
		Page<Product> pg = prodRepo.findAll(Example.of(qry), p);

		List<Product> custs = pg.getContent();
		Assertions.assertThat(custs.size()).isEqualTo(size);
	}

	@DisplayName("JUnit test for save product operation")
	@Test
	public void givenProduct_whenSave_theReturnSavedProduct() {
		Product prodSaved = prodRepo.save(product);
		prodRepo.delete(prodSaved);
		
		assertThat(prodSaved).isNotNull();
		assertThat(product.getProductName()).isEqualTo(prodSaved.getProductName());
	}

	@DisplayName("JUnit test for delete product operation")
	@Test
	public void givenProduct_whenDelete_thenRemoveProduct() {
		Product prodSaved = prodRepo.save(product);

		prodRepo.deleteById(prodSaved.getId());
		Optional<Product> optionalCust = prodRepo.findById(prodSaved.getId());
		assertThat(optionalCust).isEmpty();
	}

}
