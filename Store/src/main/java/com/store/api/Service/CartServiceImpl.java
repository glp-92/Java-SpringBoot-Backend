package com.store.api.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.api.Data.CartRepository;
import com.store.api.Data.ProductRepository;
import com.store.api.Model.Pojo.Cart;
import com.store.api.Model.Pojo.Product;
import com.store.api.Model.Request.CreateCart;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<Cart> getAllCarts() {
		return cartRepository.findAll();
	}

	@Override
	public Cart getCart(String cartId) {
		return cartRepository.getReferenceById(Long.valueOf(cartId));
	}

	@Override
	public Cart createCart(CreateCart request) {
		Cart cart = Cart.builder().products(request.getProducts()).build();
		return cartRepository.save(cart);
	}

	@Override
	public Cart addProductOnCart(String cartId, String productId) {
		Cart cart = cartRepository.getReferenceById(Long.valueOf(cartId));
		Product product = productRepository.getReferenceById(Long.valueOf(productId));
		if (product.getStock() > 0) {
			cart.getProducts().add(product);
			product.decreaseStock(1);
		}
	    return cartRepository.save(cart);
	}

	@Override
	public Cart deleteProductOnCart(String cartId, String productId) {		
		Cart cart = cartRepository.getReferenceById(Long.valueOf(cartId));
        Product product = productRepository.getReferenceById(Long.valueOf(productId));

        cart.getProducts().remove(product);
        product.increaseStock(1);
        return cartRepository.save(cart);
	}

	@Override
	public Cart deleteCart(String cartId) {
		Cart cart = cartRepository.getReferenceById(Long.valueOf(cartId));
        cartRepository.delete(cart);
        return cart;
	}

}
