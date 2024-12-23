package com.spring.productInventory.Repository;

import com.spring.productInventory.Entity.Cart;
import com.spring.productInventory.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

  //  Optional<Cart> findByCustomer(Customer customer);

    Optional<Cart> findProductsByCustomerId(Long customerId);

    Optional<Cart> findByCustomerId(Long customerId);
}
