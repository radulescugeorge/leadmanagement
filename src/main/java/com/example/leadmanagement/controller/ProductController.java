package com.example.leadmanagement.controller;

import com.example.leadmanagement.dto.ProductDto;
import com.example.leadmanagement.mapper.impl.ProductMapper;
import com.example.leadmanagement.service.ProductService;
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
        ProductDto productDtoById = productMapper.mapToDto(productService.getProductById(id));
        if(productDtoById == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDtoById);
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody ProductDto productDto){
        productService.createProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("New product added successfully.");

    }

    @PutMapping("/replace/{id}")
    public ResponseEntity<ProductDto> replaceProduct(@PathVariable long id,
                                                     @RequestBody ProductDto productDto){

        ProductDto replacedProductDto = productService.replaceProduct(id, productDto);

        if(replacedProductDto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(replacedProductDto);

    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable long id,
                                                    @RequestBody ProductDto productDto){
        ProductDto updatedProductDto = productService.updateProduct(id, productDto);

        if(updatedProductDto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedProductDto);

    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable long id){
        productService.deleteProductById(id);
    }
}
