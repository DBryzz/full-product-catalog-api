package com.bryzz.fullcatalog.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bryzz.fullcatalog.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
