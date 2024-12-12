package com.spring.productInventory.Service;

import com.spring.productInventory.Entity.Product;
import com.spring.productInventory.Entity.Supplier;
import com.spring.productInventory.Entity.User;
import com.spring.productInventory.Repository.SupplierRepository;
import com.spring.productInventory.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private UserRepository userRepository;

    public SupplierService(SupplierRepository supplierRepository, UserRepository userRepository) {
        this.supplierRepository = supplierRepository;
        this.userRepository = userRepository;
    }

    public Supplier save(Supplier supplier) {
         return supplierRepository.save(supplier);
    }


    public Optional<Supplier> getSupplierById(Long supplierId) {
        return supplierRepository.findById(supplierId);
    }

    public Supplier findByUser(User user) {
        return supplierRepository.findByUser(user);
    }
}
