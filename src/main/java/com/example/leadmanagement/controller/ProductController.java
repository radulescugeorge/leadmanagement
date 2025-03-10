package com.example.leadmanagement.controller;

import com.example.leadmanagement.dto.ProductDto;
import com.example.leadmanagement.mapper.impl.ProductMapper;
import com.example.leadmanagement.persistence.entity.Product;
import com.example.leadmanagement.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;


    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductsById(@PathVariable long id){
        Product product = productService.getProductById(id);
        if(product == null){
            throw new EntityNotFoundException("Product not found. ID="+id);
        }
        ProductDto productDtoById = productMapper.mapToDto(product);
        return ResponseEntity.ok(productDtoById);
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody ProductDto productDto){
        productService.createProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("New product added successfully.");

    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> replaceProduct(@PathVariable long id,
                                                     @RequestBody ProductDto productDto){

        ProductDto replacedProductDto = productService.replaceProduct(id, productDto);

        if(replacedProductDto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(replacedProductDto);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable long id,
                                                    @RequestBody ProductDto productDto){
        ProductDto updatedProductDto = productService.updateProduct(id, productDto);

        if(updatedProductDto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedProductDto);

    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable long id){
        productService.deleteProductById(id);
    }
}
