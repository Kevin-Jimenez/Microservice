package com.microservice.productservice.repository;

import com.microservice.productservice.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {

}
