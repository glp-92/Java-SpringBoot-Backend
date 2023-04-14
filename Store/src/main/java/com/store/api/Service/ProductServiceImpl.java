package com.store.api.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.api.Data.ProductRepository;
import com.store.api.Model.Pojo.Product;
import com.store.api.Model.Request.CreateProduct;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product createProduct(CreateProduct request) {
		Product product = Product.builder().name(request.getName()).price(request.getPrice()).stock(request.getStock()).build();		
		return productRepository.save(product);
	}

	@Override
	public Product deleteProduct(String productId) {
		Product product = productRepository.getReferenceById(Long.valueOf(productId));
		productRepository.delete(product);
		return product;
	}

	@Override
	public Product modifyProduct(String productId, CreateProduct request) {
		Product product = productRepository.getReferenceById(Long.valueOf(productId));
		product.setName(request.getName());
		product.setPrice(request.getPrice());
		product.setStock(request.getStock());
		return productRepository.save(product);
	}

}
