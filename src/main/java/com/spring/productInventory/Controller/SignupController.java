package com.spring.productInventory.Controller;

import com.spring.productInventory.Entity.Customer;
import com.spring.productInventory.Entity.Supplier;
import com.spring.productInventory.Entity.User;
import com.spring.productInventory.Service.CustomerService;
import com.spring.productInventory.Service.SupplierService;
import com.spring.productInventory.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignupController {

    private final UserService userService;
    private final CustomerService customerService;
    private final SupplierService supplierService;

    public SignupController(UserService userService, CustomerService customerService, SupplierService supplierService) {
        this.userService = userService;
        this.customerService = customerService;
        this.supplierService = supplierService;
    }

    @GetMapping("/signup")
    public String showSignupPage(Model model){
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute User user, @RequestParam String role, Model model){

        //Debugging
        System.out.println("firstName: " + user.getFirstName());
        System.out.println("lastName: " + user.getLastName());

        if(userService.isEmailExists(user.getEmail())){
            model.addAttribute("error", "Email already exists");
            return "signup";
        } else if(userService.isUsernameExists(user.getUsername())){
            model.addAttribute("error", "Username already exists");
            return "signup";
        }
        user.setRole(role);        //Save the selected role
        User savedUser = userService.registerUser(user);    //Register the user

        if ("customer".equalsIgnoreCase(role)){
            Customer customer = new Customer();
            customer.setUser(savedUser);
            customerService.save(customer);
        } else if("supplier".equalsIgnoreCase(role)){
            Supplier supplier = new Supplier();
            supplier.setUser(savedUser);
            supplierService.save(supplier);
        }
        return "signup-success";
    }
}
