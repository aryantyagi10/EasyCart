package com.spring.productInventory.Controller;

import com.spring.productInventory.Entity.Product;
import com.spring.productInventory.Entity.Supplier;
import com.spring.productInventory.Service.ProductService;
import com.spring.productInventory.Service.SupplierService;
import com.spring.productInventory.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/supplier")
@SessionAttributes("supplierId")
public class SupplierController {

    private ProductService productService;
    private SupplierService supplierService;
    private UserService userService;

    public SupplierController(ProductService productService, SupplierService supplierService, UserService userService) {
        this.productService = productService;
        this.supplierService = supplierService;
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String supplierHome(@RequestParam("supplierId") Long supplierId, Model model){
        Supplier supplier = supplierService.getSupplierById(supplierId).orElseThrow(() -> new RuntimeException("Supplier not found"));
        model.addAttribute("supplier", supplier);
        return "supplier-dashboard";
    }

    @GetMapping("/logout")
    public String showHome(){
        return "index";
    }

    @GetMapping("/add-products")
    public String showAddProductForm(@ModelAttribute("supplierId") Long supplierId, Model model){
        if (supplierId != null) {
            model.addAttribute("product", new Product());
            model.addAttribute("supplierId", supplierId);
            return "add-products";
        }
        return "redirect:/login";
    }

    @PostMapping("/add-products")
    public String addProduct(@ModelAttribute Product product, @ModelAttribute("supplierId") Long supplierId){
        if (supplierId == null){
            return "redirect:/login";
        }
        productService.addProduct(product, supplierId);
        productService.saveProduct(product, supplierId);
        return "redirect:/supplier/view-products";
    }

    @GetMapping("/view-products")
    public String viewProducts(@ModelAttribute("supplierId") Long supplierId, Model model){
        List<Product> products = productService.getProductsBySupplier(supplierId);
        model.addAttribute("products", products);
        return "view-products";
    }


    @GetMapping("/edit-product/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model){
        Product product = productService.findById(id);
        if(product != null){
            model.addAttribute("product", product);
            return "edit-product";
        }
        return "redirect:/supplier/view-products";
    }

    @PostMapping("/edit-product")
    public String updateProduct(@ModelAttribute("product") Product product){
        productService.updateProduct(product);
        return "redirect:/supplier/view-products";
    }

    @GetMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable Long id){
        productService.deleteProductById(id);
        return "redirect:/supplier/view-products";
    }
}
