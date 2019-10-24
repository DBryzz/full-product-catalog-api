package com.bryzz.fullcatalog.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bryzz.fullcatalog.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Integer> {

}
