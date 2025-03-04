package com.example.leadmanagement.controller.ui;

import com.example.leadmanagement.dto.ProductDto;
import com.example.leadmanagement.persistence.entity.Product;
import com.example.leadmanagement.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductUIController {

    private final ProductService productService;

    public ProductUIController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getAllProducts(Model model){
        List<ProductDto> products = productService.getProducts();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("product", new ProductDto());
        return "add-product";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute ProductDto productDto){
        productService.createProduct(productDto);
        return "redirect:/products";
    }

    @GetMapping("delete/{id}")
    public String deleteProduct(@PathVariable long id){
        productService.deleteProductById(id);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm (@PathVariable long id, Model model){
        Product product = productService.getProductById(id);
        if(product == null){
            return "redirect:/products";
        }
        model.addAttribute("product", product);
        return "edit-product";
    }

    @PostMapping("/update")
    public String updateProduct(@ModelAttribute ProductDto productDto){
        productService.updateProduct(productDto.getId(), productDto);
        return "redirect:/products";
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam String name, Model model) {
        List<ProductDto> products = productService.getProductsByName(name);
        model.addAttribute("products", products);
        model.addAttribute("searchQuery", name);
        return "products";
    }

}
