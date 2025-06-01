package com.telusko.titans.pms.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telusko.titans.pms.exceptions.BrandNameNotValidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.telusko.titans.pms.dto.ProductDto;
import com.telusko.titans.pms.exceptions.ProductNotFoundException;
import com.telusko.titans.pms.service.IProductService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

	@PutMapping("/product-update/{id}")
	public ResponseEntity<ProductDto> updateProduct(
			@PathVariable("id") Integer id,
			@RequestBody ProductDto productDto ) {

		ProductDto updatedProduct = service.updateProduct(id,productDto);
		return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
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

	/*
	Search by brand (brand pattern is also valid search)
	Page of products of a particular brand will be returned from DB
	Used path parameter to fetch particular brand products
	*/

	@GetMapping("/search-by/brand/{brandName}")
	public ResponseEntity<Page<ProductDto>> searchProductsByBrand(
			@PathVariable("brandName") String brandName,
			@PageableDefault(size = 10 , page = 0)Pageable pageable)  {
		Page<ProductDto> responsePageDto = service.searchProductsByBrand(brandName,pageable);
		if(responsePageDto == null || responsePageDto.getTotalElements() <= 0){
			return new ResponseEntity<>(Page.empty(), HttpStatus.NO_CONTENT);
		}else{
			return new ResponseEntity<>(responsePageDto, HttpStatus.OK);
		}
	}

	@GetMapping("/products/{matchWord}")
	public ResponseEntity<List<ProductDto>> fetchByProductName(@PathVariable("matchWord") String word){
		List<ProductDto> products = service.searchByTheName(word);

		return new ResponseEntity<>(products,HttpStatus.OK);
	}

	@GetMapping("/products/{min}/{max}")
	public ResponseEntity<Page<ProductDto>> fetchByProductPriceRange(
			@PathVariable double min, @PathVariable double max,
			@PageableDefault(page = 0, size = 5, sort = "productName", direction = Sort.Direction.ASC)
			Pageable pageable
	){
		Page<ProductDto> products = service.searchByTheProductPriceRange(min, max, pageable);
		return  new ResponseEntity<>(products,HttpStatus.OK);
	}
}
