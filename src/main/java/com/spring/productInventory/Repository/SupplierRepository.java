package com.spring.productInventory.Repository;

import com.spring.productInventory.Entity.Supplier;
import com.spring.productInventory.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    Supplier findByUser(User user);

}
