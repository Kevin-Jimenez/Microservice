package com.microservice.productservice.service;

import com.microservice.productservice.dto.ProductRequestDTO;
import com.microservice.productservice.dto.ProductResponseDTO;
import com.microservice.productservice.entity.Product;
import com.microservice.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(ProductRequestDTO productRequestDTO){
        /*Lambda*/
        Product product = Product.builder()
                .name(productRequestDTO.getName())
                .description(productRequestDTO.getDescription())
                .price(productRequestDTO.getPrice())
                .build();

        productRepository.save(product);
        log.info("Product is save");
    }

    public List<ProductResponseDTO> getAllProducts(){
        List<Product> products = productRepository.findAll();

        return products.stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }

    private ProductResponseDTO mapToProductResponse(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
