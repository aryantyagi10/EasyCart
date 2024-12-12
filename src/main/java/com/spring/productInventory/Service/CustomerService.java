package com.spring.productInventory.Service;

import com.spring.productInventory.Entity.Customer;
import com.spring.productInventory.Entity.User;
import com.spring.productInventory.Repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService{

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer findByUser(User user) {
        return customerRepository.findByUser(user);
    }
}
