package com.spring.productInventory.Service;

import com.spring.productInventory.Entity.Cart;
import com.spring.productInventory.Entity.Customer;
import com.spring.productInventory.Entity.Product;
import com.spring.productInventory.Repository.CartRepository;
import com.spring.productInventory.Repository.CustomerRepository;
import com.spring.productInventory.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private CartRepository cartRepository;
    private CustomerRepository customerRepository;
    private ProductRepository productRepository;

    public CartService(CartRepository cartRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    public void addProductToCart(Long customerId, Long productId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new RuntimeException("Customer not found"));
        Product product = productRepository.findById(productId).orElseThrow(()-> new RuntimeException("Product not found"));
        Cart cart = cartRepository.findByCustomerId(customerId).orElseGet(()-> {
            Cart newCart = new Cart();
            newCart.setCustomer(customer);
            newCart.setProducts(new ArrayList<>());
            return newCart;
        });
        if(!cart.getProducts().contains(product)){
            cart.getProducts().add(product);
        }
        cartRepository.save(cart);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Long> getProductsInCart(Long customerId) {
        return cartRepository.findProductsByCustomerId(customerId).map(cart -> cart.getProducts().stream().map(Product::getId).collect(Collectors.toList())).orElse(Collections.emptyList()); //Returns an empty list if no cart is found//
    }

    public void removeProductFromCart(Long customerId, Long productId) {
        Cart cart = cartRepository.findByCustomerId(customerId).orElseThrow(()-> new RuntimeException("cart not found"));
        cart.getProducts().removeIf(product -> product.getId().equals(productId));
        cartRepository.save(cart);
    }

    public void clearCart(Long customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId).orElseThrow(()-> new RuntimeException("Cart not found"));
        cart.getProducts().clear();
        cartRepository.save(cart);
    }

}
