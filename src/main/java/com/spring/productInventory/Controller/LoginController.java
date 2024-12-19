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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("supplierId")
public class LoginController {

    private UserService userService;
    private CustomerService customerService;
    private SupplierService supplierService;

    public LoginController(UserService userService, CustomerService customerService, SupplierService supplierService) {
        this.userService = userService;
        this.customerService = customerService;
        this.supplierService = supplierService;
    }

    @GetMapping("/login")
    public String showLoginPage(){
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, Model model) {
        User user = userService.authenticateUser(email, password);
        if(user != null){
            if ("customer".equalsIgnoreCase(user.getRole())){
                Customer customer = customerService.findByUser(user);
                if (customer != null){
                    model.addAttribute("customerId", customer.getId());
                    return "customer-dashboard";
                }
            } else if("supplier".equalsIgnoreCase(user.getRole())){
                Supplier supplier = supplierService.findByUser(user);
                if (supplier != null) {
                    model.addAttribute("supplierId", supplier.getId());
                    return "redirect:/supplier/dashboard?supplierId=" + supplier.getId();
                }
            }
        }
        model.addAttribute("error", "Invalid email or password");
        return "login";
    }
}
