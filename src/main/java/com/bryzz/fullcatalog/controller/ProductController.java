package com.bryzz.fullcatalog.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
//import org.springframework.hateoas.EntityModel; // replaces import org.springframework.hateoas.Resource;
//import org.springframework.hateoas.server.reactive.WebFluxLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bryzz.fullcatalog.entity.Category;
import com.bryzz.fullcatalog.entity.Product;
import com.bryzz.fullcatalog.exceptions.ResourceNotFoundException;
import com.bryzz.fullcatalog.repo.CategoryRepo;
import com.bryzz.fullcatalog.repo.ProductRepo;
import com.bryzz.fullcatalog.service.CategoryDaoService;
import com.bryzz.fullcatalog.service.ProductDaoService;

@RestController
@RequestMapping("/api")
public class ProductController {
	
	@Autowired
	ProductDaoService productService;
	
	@Autowired
	ProductRepo productRepo; 
	
	
	@GetMapping("/products")
	public List<Product> retrieveProducts() {
		return productService.getProducts();
	}
	
	@GetMapping(value = "/downloadFile/{fileName:.+}")
    public ResponseEntity<org.springframework.core.io.Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        return productService.showImage(fileName, request);
    }
	
	@GetMapping("/products/{productId}")
	public Resource<Product> retrieveProduct(@PathVariable int productId) {
		return productService.getProduct(productId);
	}
	
	@DeleteMapping("/products/{productId}")
	public ResponseEntity<Object> deleteProduct(@PathVariable int productId) {
		return productService.removeProduct(productId);
	}
	
	@PostMapping("/products/category/{categoryId}")
	public ResponseEntity<Object> createCategory(@PathVariable int categoryId,  @RequestParam String name, @RequestParam int quantity,
			@RequestParam long price, @RequestPart MultipartFile pxtImage) {
		
		return productService.postProduct(categoryId, name, quantity, price, pxtImage);
		
	}
	
	@PutMapping("/products/{productId}/category/{categoryId}")
	public ResponseEntity<Object> updateCategory(@PathVariable int productId, @PathVariable int categoryId, @RequestParam String name, 
			@RequestParam int quantity, @RequestParam long price, @RequestPart MultipartFile pxtImage) {
		
		return productService.editProduct(productId, categoryId, name, quantity, price, pxtImage);
		
	}

}
