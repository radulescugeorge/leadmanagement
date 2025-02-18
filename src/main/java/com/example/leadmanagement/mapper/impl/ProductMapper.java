package com.example.leadmanagement.mapper.impl;

import com.example.leadmanagement.dto.ProductDto;
import com.example.leadmanagement.mapper.ObjectMapper;
import com.example.leadmanagement.persistence.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements ObjectMapper<ProductDto, Product> {
    @Override
    public ProductDto mapToDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice()
        );
    }

    @Override
    public Product mapToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        return product;
    }
}
