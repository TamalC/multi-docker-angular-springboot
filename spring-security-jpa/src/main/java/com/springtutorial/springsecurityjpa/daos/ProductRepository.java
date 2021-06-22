package com.springtutorial.springsecurityjpa.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springtutorial.springsecurityjpa.models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

}
