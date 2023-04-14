package com.store.api.Data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.api.Model.Pojo.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
