package com.jctech.eshop.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jctech.eshop.model.Product;
import com.jctech.eshop.repo.ProductRepo;

public interface ProductService {

	void setProdRepo(ProductRepo prodRepo);

	Product findProductById(Integer id);

	Product findProductByCode(String prodCode);

	List<Product> getAllProducts();

	Page<Product> getProductsInPage(Integer productId, String category, Pageable pageable);

	void deleteProductById(Integer id);

	Product saveOrUpdateProduct(Product prod);

	boolean existsById(Integer id);

}