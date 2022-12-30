package com.jctech.eshop.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; 
import org.springframework.stereotype.Repository;
 
import com.jctech.eshop.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
	
	public List<Product> findAll();

	Page<Product> findAll(Pageable p);

	@Query(value = "SELECT * FROM products where product_name = ?", nativeQuery = true)
	public Product findByName(String name);

	@Query(value = "SELECT * FROM products where category = ?", nativeQuery = true)
	public List<Product> findByCategoryContaining(String category);

	@Query(value = "SELECT * FROM products where product_code = ?", nativeQuery = true)
	public Product findByProductCode(String prodCode);

	public boolean existsById(Integer id);
}
