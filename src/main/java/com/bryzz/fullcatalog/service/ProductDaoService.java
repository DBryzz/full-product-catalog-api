package com.bryzz.fullcatalog.service;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.hateoas.EntityModel; // replaces import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resource;
//import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bryzz.fullcatalog.entity.Category;
import com.bryzz.fullcatalog.entity.Product;
import com.bryzz.fullcatalog.exceptions.ResourceNotFoundException;
import com.bryzz.fullcatalog.fileservice.FileStorageService;
import com.bryzz.fullcatalog.repo.CategoryRepo;
import com.bryzz.fullcatalog.repo.ProductRepo;

@Service
public class ProductDaoService {

	private static final Logger logger = LoggerFactory.getLogger(ProductDaoService.class);

	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	CategoryRepo categoryRepo;
	
	@Autowired
	Category category;
	
	@Autowired
    private FileStorageService fileStorageService;
	
	
    public ResponseEntity<org.springframework.core.io.Resource> showImage(String fileName, HttpServletRequest request) {
        // Load file as Resource
    	org.springframework.core.io.Resource  resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
	
	
	
	public List<Product> getProducts() {
		return productRepo.findAll();
		
	}
	
	public Resource<Product> getProduct(int id) {
		Optional<Product> product = productRepo.findById(id);
		
		if(!product.isPresent()) {
			throw new ResourceNotFoundException("id - " +id);
		}
		
		Resource<Product> resource = new Resource<Product>(product.get());
		//ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getCategories());
		//resource.add(linkTo.withRel("all-categories"));
		
		return resource;
	}
	
	public ResponseEntity<Object> removeProduct(int id) {
		Optional<Product> delProduct = productRepo.findById(id);
		productRepo.deleteById(id);
		return new ResponseEntity<>(delProduct, HttpStatus.NO_CONTENT);
	}

	/*
	@PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }
    */
	
	public ResponseEntity<Object> postProduct(int id, String name, int quantity, long price,  MultipartFile pxtImage) { //@Valid
		
		String fileName = fileStorageService.storeFile(pxtImage);
		Optional<Category> category_1 = categoryRepo.findById(id);
		if(!category_1.isPresent()) {
			throw new ResourceNotFoundException("id - " +id);
		}
		
		String productUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/downloadFile/")
                .path(fileName)
                .toUriString();
		
		Product newProduct = new Product(name, quantity, price, productUri);		//Product(fileName, productUri, file.getContentType(), file.getSize())
		 
		newProduct.putCategory(category_1.get());
		newProduct = productRepo.save(newProduct);
		
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(newProduct.getPxtId())
				.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	
public ResponseEntity<Object> editProduct(int pxtId, int catId, String name, int quantity, long price,  MultipartFile pxtImage) {
		
		Optional<Product> product = productRepo.findById(pxtId);
		if(product.isPresent()) {
			
			Optional<Category> category_1 = categoryRepo.findById(catId);
			if(!category_1.isPresent()) {
				throw new ResourceNotFoundException("category_id - " +catId);
			}
			
			
			String fileName = fileStorageService.storeFile(pxtImage);
			
			Product productUpdate = product.get();
			productUpdate.setPxtName(name);
			productUpdate.setQuantity(quantity);
			productUpdate.setPrice(price);
			
			String productUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	                .path("/api/downloadFile/")
	                .path(fileName)
	                .toUriString();
			
			
			productUpdate.setPxtUri(productUri);
			 
			//productUpdate = productRepo.save(productUpdate);
			
			
			
			return new ResponseEntity<>(productRepo.save(productUpdate), HttpStatus.NO_CONTENT);
			
		}
		throw new ResourceNotFoundException("product_id - " +pxtId);	
	}
	

	
	public ResponseEntity<Object> editProduct(int pxtId, int catId, Product newProduct) {
		
		Optional<Product> product = productRepo.findById(pxtId);
		if(product.isPresent()) {
			
			Optional<Category> category_1 = categoryRepo.findById(catId);
			if(!category_1.isPresent()) {
				throw new ResourceNotFoundException("category_id - " +catId);
			}
			
	//		newProduct.putCategory(category.get());
			
			
			Product productUpdate = product.get();
			productUpdate.setPxtName(newProduct.getPxtName());
			productUpdate.setQuantity(newProduct.getQuantity());
			productUpdate.setPrice(newProduct.getPrice());
			Category newCat = new Category(category_1.get().getCatId(), category_1.get().getCatName());
			newCat.setCatId(product.get().getCatId());
			newCat.setCatName(product.get().getCatName());
			productUpdate.putCategory(newCat);
			
			return new ResponseEntity<>(productRepo.save(productUpdate), HttpStatus.NO_CONTENT);
			
		}
		throw new ResourceNotFoundException("product_id - " +pxtId);
		
		
		 
		
		
	}
	
	
	
}
