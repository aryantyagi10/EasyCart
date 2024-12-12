package com.spring.productInventory.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerController {

    @GetMapping("/customer-logout")
    public String showLogin(){
        return "index";
    }
}
