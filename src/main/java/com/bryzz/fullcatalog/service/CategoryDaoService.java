package com.bryzz.fullcatalog.service;

//import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Resource;
//import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bryzz.fullcatalog.entity.Category;
import com.bryzz.fullcatalog.exceptions.ResourceNotFoundException;
import com.bryzz.fullcatalog.repo.CategoryRepo;

@Service
public class CategoryDaoService {

	private static List<Category> categories = new ArrayList<>();
	
	@Autowired
	CategoryRepo categoryRepo;
	
	@Autowired
	Category category;
	
	public List<Category> getCategories() {
		return categoryRepo.findAll();
	}
	
	public Resource<Category> getCategory(int id) {
		Optional<Category> category = categoryRepo.findById(id);
		
		if(!category.isPresent()) {
			throw new ResourceNotFoundException("id - " +id);
		}
		
		Resource<Category> resource = new Resource<Category>(category.get());
		//ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getCategories());
		//resource.add(linkTo.withRel("all-categories"));
		
		return resource;
	}
	
	public ResponseEntity<Object> removeCategory(int id) {
		Optional<Category> delCategory = categoryRepo.findById(id);
		categoryRepo.deleteById(id);
		return new ResponseEntity<>(delCategory, HttpStatus.NO_CONTENT);
	}
	
	public ResponseEntity<Object> postCategory(Category category) { //@Valid
		Category newCategory = categoryRepo.save(category);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(newCategory.getCatId())
				.toUri();
		
		return ResponseEntity.created(location).build();
		// OR	return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
	}
	
	public ResponseEntity<Object> editCategory(int id, Category categoryName) {
		Optional<Category> category = categoryRepo.findById(id);
		Category categoryUpdate = category.get();
		categoryUpdate.setCatName(categoryName.getCatName());
		return new ResponseEntity<>(categoryRepo.save(categoryUpdate), HttpStatus.NO_CONTENT);
		
	}
}
