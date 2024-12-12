package com.spring.productInventory.Service;

import com.spring.productInventory.Entity.Product;
import com.spring.productInventory.Entity.Supplier;
import com.spring.productInventory.Repository.ProductRepository;
import com.spring.productInventory.Repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private SupplierRepository supplierRepository;

    public ProductService(ProductRepository productRepository, SupplierRepository supplierRepository) {
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
    }

    public Product addProduct(Product product, Long supplierId){
        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow(()->new RuntimeException("Supplier not found"));
        product.setSupplier(supplier);
        return productRepository.save(product);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public void saveProduct(Product product, Long supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow(()-> new RuntimeException("Supplier not found"));
        product.setSupplier(supplier);
        productRepository.save(product);
    }

    public Optional<Product> getProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        System.out.println("Product fetched: " + optionalProduct);
        return optionalProduct;
    }

    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getProductsBySupplier(Long supplierId) {
        return productRepository.findBySupplier_Id(supplierId);
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product Not found"));
    }
}
