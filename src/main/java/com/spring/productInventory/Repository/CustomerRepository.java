package com.spring.productInventory.Repository;

import com.spring.productInventory.Entity.Customer;
import com.spring.productInventory.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByUser(User user);
}
