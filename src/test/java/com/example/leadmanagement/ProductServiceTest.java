package com.example.leadmanagement;

import com.example.leadmanagement.dto.ProductDto;
import com.example.leadmanagement.mapper.impl.ProductMapper;
import com.example.leadmanagement.persistence.entity.Lead;
import com.example.leadmanagement.persistence.entity.Product;
import com.example.leadmanagement.persistence.repository.ProductRepository;
import com.example.leadmanagement.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @Test
    void getProducts_ShouldReturnMappedDto(){
        List<Lead>  leadList = List.of(
                new Lead(),
                new Lead()
        );

        List<Product> productList = List.of(
                new Product(1L, "TestProduct1", 12.34,leadList),
                new Product(2L,"TestProduct2", 23.45, leadList)
        );

        List<ProductDto> expectedDtos = List.of(
                new ProductDto(1L, "TestProduct1", 12.34),
                new ProductDto(2L,"TestProduct2", 23.45)
        );

        when(productRepository.findAllProducts()).thenReturn(productList);
        when(productMapper.mapToDto(productList.get(0))).thenReturn(expectedDtos.get(0));
        when(productMapper.mapToDto(productList.get(1))).thenReturn(expectedDtos.get(1));

        List<ProductDto> result = productService.getProducts();

        assertEquals(expectedDtos, result);

        verify(productRepository, times(1)).findAllProducts();
        verify(productMapper, times(2)).mapToDto(any(Product.class));

    }

}
