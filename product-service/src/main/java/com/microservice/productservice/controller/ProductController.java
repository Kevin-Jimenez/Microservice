package com.microservice.productservice.controller;

import com.microservice.productservice.dto.ProductRequestDTO;
import com.microservice.productservice.dto.ProductResponseDTO;
import com.microservice.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProdcut(@RequestBody ProductRequestDTO productRequestDTO){
        productService.createProduct(productRequestDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponseDTO> getAllProducts(){
        return productService. getAllProducts();
    }
}
