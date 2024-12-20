package com.spring.productInventory.Controller;

import com.spring.productInventory.Entity.Customer;
import com.spring.productInventory.Entity.Product;
import com.spring.productInventory.Entity.Supplier;
import com.spring.productInventory.Entity.User;
import com.spring.productInventory.Service.CustomerService;
import com.spring.productInventory.Service.ProductService;
import com.spring.productInventory.Service.UserService;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/customer")
@SessionAttributes("customerId")
public class CustomerController {

    private ProductService productService;
    private CustomerService customerService;
    private UserService userService;

    public CustomerController(ProductService productService, CustomerService customerService, UserService userService) {
        this.productService = productService;
        this.customerService = customerService;
        this.userService = userService;
    }

    @GetMapping("/logout")
    public String showLogin(){
        return "index";
    }

    @GetMapping("/dashboard")
    public String customerHome(@RequestParam("customerId") Long customerId, Model model){
        Customer customer = customerService.getCustomerById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        model.addAttribute("customer", customer);
        return "customer-dashboard";
    }

    @GetMapping("/profile")
    public String showProfilePage(@RequestParam("customerId") Long customerId,Model model){
        Customer customer = customerService.getCustomerById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        User user = userService.getUserById(customer.getUser().getId());
        model.addAttribute("customer", customer);
        model.addAttribute("user", user);
        return "customer-profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@RequestParam("customerId") Long customerId, @RequestParam("addressLine1") String addressLine1, @RequestParam("addressLine2") String addressLine2, @RequestParam("city") String city, @RequestParam("postalCode") String postalCode, @RequestParam("country") String country,RedirectAttributes redirectAttributes){
        Customer customer = customerService.getCustomerById(customerId).orElseThrow(()-> new RuntimeException("Customer not found"));
        customerService.update(customerId, addressLine1, addressLine2, city, postalCode, country);
        redirectAttributes.addAttribute("customerId", customer.getId());
        return "redirect:/customer/profile";
    }

    @GetMapping("/products")
    public String browseProducts(Model model){
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "browse-products";
    }
}
