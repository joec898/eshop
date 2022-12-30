package com.jctech.eshop.ttd.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import com.jctech.eshop.model.Product;
import com.jctech.eshop.repo.ProductRepo;
import com.jctech.eshop.service.ProductServiceImpl;

/**
 * @author jc
 */

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // https://howtodoinjava.com/spring-boot2/testing/datajpatest-annotation/
@ActiveProfiles("test")
public class TtdProductServiceDataJpaTest {

	private Product product;

	@Autowired
	private ProductRepo prodRepo;

	@InjectMocks
	ProductServiceImpl prodService;

	@BeforeEach
	public void setUp() {
		prodService.setProdRepo(prodRepo);
		product = new Product(600, "P0", "Nikon D830", "DESC", 1167L, 1123L, 75, 10, 10, 50, 1, "Camera");
	}

	@AfterEach
	public void tearDown() {
		prodService = null;
		product = null;
	}

	@DisplayName("JUnit test service for get product by id operation")
	@Test
	public void givenProduct_whenFindById_returnProduct() throws Exception{
		Product prodSaved = prodService.saveOrUpdateProduct(product);
		Product prod = prodService.findProductById(prodSaved.getId());
		prodService.deleteProductById(prodSaved.getId());

		Assertions.assertThat(prod.getProductCode()).isEqualTo(prodSaved.getProductCode());
	}

	@DisplayName("JUnit test service for get all products in pages operation")
	@Test
	public void givenPage_PageSize_whenFindAll_thenReturnProductsInPage() throws Exception{
		int pageSize = 5;
		int totalPage = 4;
		for (int page = 0; page < totalPage; page++) {
			Pageable p = PageRequest.of(page, pageSize);
			Page<Product> pg = prodService.getProductsInPage(null, null, p);
			List<Product> custs = pg.getContent();

			Assertions.assertThat(custs.size()).isEqualTo(pageSize);
			Assertions.assertThat(pg.getNumber()).isEqualTo(page);
		}
	}

	@DisplayName("JUnit test service for save product operation")
	@Test
	public void givenProduct_whenSave_theReturnSavedProduct() throws Exception{
		Product prodSaved = prodService.saveOrUpdateProduct(product);
		prodService.deleteProductById(prodSaved.getId());

		assertThat(prodSaved).isNotNull();
		assertThat(product.getProductName()).isEqualTo(prodSaved.getProductName());
	}

	@DisplayName("JUnit test service for delete product operation")
	@Test
	public void givenProduct_whenDelete_thenRemoveProduct() throws Exception{
		Product prodSaved = prodRepo.save(product);

		prodRepo.deleteById(prodSaved.getId());

		Optional<Product> optionalCust = prodRepo.findById(prodSaved.getId());
		assertThat(optionalCust).isEmpty();
	}
}
