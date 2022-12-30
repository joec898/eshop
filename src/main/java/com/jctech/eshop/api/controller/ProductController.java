package com.jctech.eshop.api.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jctech.eshop.exception.ObjectNotFoundException;
import com.jctech.eshop.model.Product;
import com.jctech.eshop.model.ProductResponse;
import com.jctech.eshop.model.response.OperationResponse;
import com.jctech.eshop.model.response.OperationResponse.ResponseStatusEnum;
import com.jctech.eshop.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = {"Products"})
@RestController
@RequestMapping("/api")
public class ProductController {
	
	@Autowired
	private ProductService prodService;

	@ApiOperation(value = "List of products in pages", response = ProductResponse.class)
    @GetMapping("/products")
	public ProductResponse getProductsByPage(
			@ApiParam(value = "")
			@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
			@ApiParam(value = "between 1 to 1000") 
			@RequestParam(value = "size", defaultValue = "20", required = false) Integer size,
			@RequestParam(value = "productid", required = false) Integer productId,
			@RequestParam(value = "category", required = false) String category, Pageable pageable) {
		
		ProductResponse resp = new ProductResponse();
		Page<Product> productPage = prodService.getProductsInPage(productId, category, pageable);
		resp.setPageStats(productPage, true);
		resp.setItems(productPage.getContent());
		return resp;
	}
	
	@ApiOperation(value = "List of products", response = ProductResponse.class)
	@GetMapping("/allproducts")
	public ResponseEntity<List<Product>> getProducts() throws Exception {
		List<Product> prods = new ArrayList<Product>();
		//prods = prodService.getAllProducts();
		return new ResponseEntity<>(prods, HttpStatus.OK);
	}
	
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable String id) throws Exception {
		Product prod = prodService.findProductById(Integer.valueOf(id)); 
		return new ResponseEntity<>(prod, HttpStatus.OK);
	}
	
	@GetMapping("/productbycode/{prodcode}")
	public ResponseEntity<Product> getProductByCode(@PathVariable String prodcode) throws Exception {
		Product prod = prodService.findProductByCode(prodcode); 
		return new ResponseEntity<>(prod, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Add new product", response = OperationResponse.class)
	@PostMapping("/products")
	public OperationResponse addNewProduct(@RequestBody Product product, HttpServletRequest req) {
		OperationResponse resp = new OperationResponse();
		if (this.prodService.existsById(product.getId())) {
			resp.setOperationMessage("Unable to add Product -- Product already exists.");
			resp.setOperationStatus(ResponseStatusEnum.ERROR);
		} else {
			this.prodService.saveOrUpdateProduct(product);
			resp.setOperationMessage("Product Added");
			resp.setOperationStatus(ResponseStatusEnum.SUCCESS);
		}
		return resp;
	} 
	
	@ApiOperation(value = "Update a product", response = OperationResponse.class)
	@PutMapping("/products")
	public OperationResponse updateProduct(@RequestBody Product product, HttpServletRequest req) {
		OperationResponse resp = new OperationResponse();
		if (this.prodService.existsById(product.getId())) {
			this.prodService.saveOrUpdateProduct(product);
			resp.setOperationMessage("Product Updated");
			resp.setOperationStatus(ResponseStatusEnum.SUCCESS);
		} else {
			resp.setOperationMessage("Unable to update Product -- Product does not exist.");
			resp.setOperationStatus(ResponseStatusEnum.ERROR);
		}
		return resp;
	}
	
	@ApiOperation(value = "Delete a product", response = OperationResponse.class)
	@DeleteMapping("products/{id}") 
	public OperationResponse deleteProductById(@PathVariable Integer id, HttpServletRequest req) {
		OperationResponse resp = new OperationResponse();
		if (this.prodService.existsById(id) ){
            this.prodService.deleteProductById(id);
            resp.setOperationStatus(ResponseStatusEnum.SUCCESS);
            resp.setOperationMessage("Product Deleted");
        }
        else{
            resp.setOperationStatus(ResponseStatusEnum.ERROR);
            resp.setOperationMessage("No Product Exist");
        }
		
		return resp;
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	private void ObjectNotFoundHandler(ObjectNotFoundException ex) {
		System.out.println("Product not found");
	}
	
	
}
