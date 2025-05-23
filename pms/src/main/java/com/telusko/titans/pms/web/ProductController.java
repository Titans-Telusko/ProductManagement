package com.telusko.titans.pms.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.telusko.titans.pms.dto.ProductDto;
import com.telusko.titans.pms.exceptions.ProductNotFoundException;
import com.telusko.titans.pms.service.IProductService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ProductController {

	@Autowired
	IProductService service;

	@PostMapping("/product")
	ResponseEntity<ProductDto> addProduct(
			@RequestParam("product") String productJson,
			@RequestParam("image") MultipartFile imageFile
	) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		ProductDto productDto = mapper.readValue(productJson, ProductDto.class);
		productDto.setProductImage(imageFile.getBytes());

		ProductDto dto = service.addProduct(productDto);
		return new ResponseEntity<>(dto, HttpStatus.CREATED);
	}

	@GetMapping("/products")
	ResponseEntity<Page<ProductDto>> getAllProducts(@PageableDefault(size = 10, page = 0) Pageable pageable) {
		Page<ProductDto> dto = service.getAllProducts(pageable);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("/product/{id}")
	ResponseEntity<ProductDto> getProduct(@PathVariable int id) {
		ProductDto dto = service.getProductById(id);
		if (dto == null) {
			throw new ProductNotFoundException("Product with Id " + id + " does not exist");
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);

	}

}
