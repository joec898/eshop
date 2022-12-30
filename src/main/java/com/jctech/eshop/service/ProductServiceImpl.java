package com.jctech.eshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; 
import org.springframework.stereotype.Service;

import com.jctech.eshop.exception.ObjectNotFoundException; 
import com.jctech.eshop.model.Product;
import com.jctech.eshop.repo.ProductRepo;

@Service
public class ProductServiceImpl implements ProductService {

	private ProductRepo prodRepo;

	@Override
	@Autowired
	public void setProdRepo(ProductRepo prodRepo) {
		this.prodRepo = prodRepo;
	}

	@Override
	public Product findProductById(Integer id) {
		Product prod = null;
		Optional<Product> optionalProd = prodRepo.findById(id);
		if (optionalProd.isPresent()) {
			prod = optionalProd.get();
		} else {
			throw new ObjectNotFoundException("Product by id " + id + " was not found");
		}
		return prod;
	}

	@Override
	public Product findProductByCode(String prodCode) {
		Product prod = prodRepo.findByProductCode(prodCode);
		if (prod == null) {
			throw new ObjectNotFoundException("Product by product code " + prodCode + " was not found");
		}
		return prod;
	}

	@Override
	@Cacheable("Products")
	public List<Product> getAllProducts() {
		return (List<Product>) prodRepo.findAll();
	}


	@Override
	public Page<Product> getProductsInPage(Integer productId, String category, Pageable pageable) {  
		Product qry = new Product();
        if (productId != null)  { qry.setId(productId); }
        if (category  != null)  { qry.setCategory(category); }
		
	    Page<Product> pg = prodRepo.findAll(Example.of(qry), pageable);
		return pg;
	}

	@Override
	@CacheEvict(value = "Products", allEntries = true)
	public void deleteProductById(Integer id) {
		prodRepo.deleteById(id); 
	}

	@Override
	@CacheEvict(value = "Products", allEntries = true)
	public Product saveOrUpdateProduct(Product prod) { 
		return (Product) prodRepo.save(prod);
	} 
	
	@Override
	public boolean existsById(Integer id) { 
		return prodRepo.existsById(id);
	}

}
