package com.store.api.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.store.api.Model.Pojo.Product;
import com.store.api.Model.Request.CreateProduct;
import com.store.api.Service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductController {
	/*List<Product> getAllProducts();
	Product createProduct(CreateProduct request);
	Product deleteProduct(String productId);
	Product modifyProduct(String productId, CreateProduct request);*/
	
	private final ProductService productService;
	
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getProducts () {
		try {
			List<Product> products = productService.getAllProducts();
			return ResponseEntity.status(HttpStatus.OK).body(products);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostMapping("/products")
	public ResponseEntity<Product> createProduct (
			@RequestBody CreateProduct request) {
		try {
			Product product = productService.createProduct(request);
			return ResponseEntity.status(HttpStatus.CREATED).body(product);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	@DeleteMapping("/products/{productId}") 
	public ResponseEntity<Product> deleteProductEntity (
			@PathVariable String productId) {
		try {
			Product product = productService.deleteProduct(productId);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
