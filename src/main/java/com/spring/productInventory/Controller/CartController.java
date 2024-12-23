package com.spring.productInventory.Controller;

import com.spring.productInventory.Entity.Customer;
import com.spring.productInventory.Entity.Product;
import com.spring.productInventory.Service.CartService;
import com.spring.productInventory.Service.CustomerService;
import com.spring.productInventory.Service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customer")
@SessionAttributes("customerId")
public class CartController {

    private CartService cartService;
    private CustomerService customerService;
    private ProductService productService;

    public CartController(CartService cartService, CustomerService customerService, ProductService productService) {
        this.cartService = cartService;
        this.customerService = customerService;
        this.productService = productService;
    }

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam("customerId") Long customerId, @RequestParam("productId") Long productId, Model model){
        try{
            cartService.addProductToCart(customerId, productId);
            model.addAttribute("message", "Product added to cart successfully");
        } catch (Exception e){
            model.addAttribute("error", "Failed to add product to cart" + e.getMessage());
        }
        return "redirect:/customer/products?customerId=" + customerId;
    }

    @GetMapping("/cart")
    public String viewCart(@RequestParam("customerId") Long customerId, Model model){
        Customer customer = customerService.getCustomerById(customerId).orElseThrow(()-> new RuntimeException("Customer not found"));
        List<Long> cartProductIds = cartService.getProductsInCart(customerId);
        List<Product> cartProducts = productService.getProductByIds(cartProductIds);
        double totalPrice = cartProducts.stream().mapToDouble(Product::getPrice).sum();
        model.addAttribute("customer", customer);
        model.addAttribute("cartProducts", cartProducts);
        model.addAttribute("totalPrice", totalPrice);
        return "cart";
    }

    @PostMapping("/remove-from-cart")
    public String removeFromCart(@RequestParam Long productId, @RequestParam Long customerId){
        cartService.removeProductFromCart(customerId, productId);
        return "redirect:/customer/cart?customerId=" + customerId;
    }

    @PostMapping("/place-order")
    public String placeOrder(@RequestParam Long customerId, Model model){
        Customer customer = customerService.getCustomerById(customerId).orElseThrow(()-> new RuntimeException("Customer not found"));
        List<Long> cartProductIds = cartService.getProductsInCart(customerId);
        List<Product> orderedProducts = productService.getProductByIds(cartProductIds);
        cartService.clearCart(customerId);
        model.addAttribute("orderedProducts", orderedProducts);
        model.addAttribute("customer", customer);
        return "order-summary";
    }
}
