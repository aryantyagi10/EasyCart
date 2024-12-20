package com.spring.productInventory.Service;

import com.spring.productInventory.Entity.Customer;
import com.spring.productInventory.Entity.Product;
import com.spring.productInventory.Entity.User;
import com.spring.productInventory.Repository.CustomerRepository;
import com.spring.productInventory.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService{

    private CustomerRepository customerRepository;
    private ProductRepository productRepository;

    public CustomerService(CustomerRepository customerRepository, ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer findByUser(User user) {
        return customerRepository.findByUser(user);
    }

    public Optional<Customer> getCustomerById(Long customerId) {
        return customerRepository.findById(customerId);
    }

    public Customer update(Long customerId, String addressLine1, String addressLine2, String city, String postalCode, String country) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new RuntimeException("Customer not found"));
        customer.setAddressLine1(addressLine1);
        customer.setAddressLine2(addressLine2);
        customer.setCity(city);
        customer.setPostalCode(postalCode);
        customer.setCountry(country);
        return customerRepository.save(customer);
    }

}
