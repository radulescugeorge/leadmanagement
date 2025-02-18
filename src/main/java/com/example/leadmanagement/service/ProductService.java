package com.example.leadmanagement.service;

import com.example.leadmanagement.dto.ProductDto;
import com.example.leadmanagement.mapper.ObjectMapper;
import com.example.leadmanagement.persistence.entity.Product;
import com.example.leadmanagement.persistence.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ObjectMapper<ProductDto, Product> productMapper;

    public ProductService(ProductRepository productRepository, ObjectMapper<ProductDto, Product> productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public List<ProductDto> getProducts(){
        return productRepository.findAllProducts().stream()
                .map(productMapper::mapToDto)
                .toList();
    }

    public List<ProductDto> getProductByName(String name){
        List<Product> list = productRepository.findProductByName(name);
        return list.stream()
                .map(productMapper::mapToDto)
                .toList();
    }

    public Product getProductById(long id){
        return productRepository.findById(id).orElse(null);
    }

    public ProductDto createProduct(ProductDto productDto){
        Product newProduct = productMapper.mapToEntity(productDto);
        return productMapper.mapToDto(productRepository.save(newProduct));
    }

    public ProductDto replaceProduct(long id, ProductDto productDto){
        Product existingProduct = getProductById(id);

        existingProduct.setName(productDto.getName());
        existingProduct.setPrice(productDto.getPrice());

        return productMapper.mapToDto(productRepository.save(existingProduct));
    }

    public ProductDto updateProduct(long id, ProductDto productDto){
        Product existingProduct = getProductById(id);

        if(productDto.getName() != null){
            existingProduct.setName(productDto.getName());
        }

        if(productDto.getPrice() >= 0 ){
            existingProduct.setPrice(productDto.getPrice());
        }

        return productMapper.mapToDto(productRepository.save(existingProduct));
    }

    public void deleteProductById(long id){
        productRepository.deleteById(id);
    }
}
