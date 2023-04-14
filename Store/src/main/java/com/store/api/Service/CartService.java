package com.store.api.Service;

import java.util.List;

import com.store.api.Model.Pojo.Cart;
import com.store.api.Model.Request.CreateCart;

public interface CartService {
	List<Cart> getAllCarts();
	Cart getCart(String cartId);
	Cart createCart(CreateCart request);
	Cart addProductOnCart(String cartId, String productId);
	Cart deleteProductOnCart(String cartId, String productId);
	Cart deleteCart(String cartId);
}
