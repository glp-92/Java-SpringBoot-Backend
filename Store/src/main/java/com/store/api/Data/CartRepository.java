package com.store.api.Data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.api.Model.Pojo.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
