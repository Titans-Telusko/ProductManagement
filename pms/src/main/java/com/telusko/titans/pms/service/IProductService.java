package com.telusko.titans.pms.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import com.telusko.titans.pms.dto.ProductDto;
public interface IProductService {
	
	ProductDto addProduct(ProductDto brand);

	Page<ProductDto> getAllProducts(Pageable pageable);

	ProductDto getProductById(int id);
}
