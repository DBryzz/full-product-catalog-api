package com.bryzz.fullcatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.bryzz.fullcatalog.property.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class FullProductCatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(FullProductCatalogApplication.class, args);
	}

}
