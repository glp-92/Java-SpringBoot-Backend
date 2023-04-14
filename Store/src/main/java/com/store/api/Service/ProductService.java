package com.store.api.Service;

import java.util.List;

import com.store.api.Model.Pojo.Product;
import com.store.api.Model.Request.CreateProduct;

public interface ProductService {
	List<Product> getAllProducts();
	Product createProduct(CreateProduct request);
	Product deleteProduct(String productId);
	Product modifyProduct(String productId, CreateProduct request);
}
