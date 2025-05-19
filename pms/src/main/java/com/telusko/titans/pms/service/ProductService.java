package com.telusko.titans.pms.service;

import org.springframework.stereotype.Service;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.telusko.titans.pms.dto.ProductDto;
import com.telusko.titans.pms.model.Product;
import com.telusko.titans.pms.model.ProductBrand;
import com.telusko.titans.pms.model.ProductCategory;
import com.telusko.titans.pms.repo.IProductBrandRepo;
import com.telusko.titans.pms.repo.IProductCategoryRepo;
import com.telusko.titans.pms.repo.IProductRepo;
import com.telusko.titans.pms.utils.ProductUtility;

@Service
public class ProductService implements IProductService {

	@Autowired
	IProductRepo productRepo;

	@Autowired
	IProductCategoryRepo categoryRepo;

	@Autowired
	IProductBrandRepo brandRepo;

	@Override
	public ProductDto addProduct(ProductDto requestDto) {

		ProductBrand brand = brandRepo.findByBrandNameIgnoreCase(requestDto.getProductBrand().getBrandName());

		if (brand == null) {
			brand = new ProductBrand();
			BeanUtils.copyProperties(requestDto.getProductBrand(), brand);
			brand = brandRepo.save(brand);
		}

		ProductCategory category = categoryRepo
				.findByCategoryNameIgnoreCase(requestDto.getProductCategory().getCategoryName());

		if (category == null) {
			category = new ProductCategory();
			BeanUtils.copyProperties(requestDto.getProductCategory(), category);
			category = categoryRepo.save(category);
		}

		Product product = ProductUtility.constructProductModel(requestDto, brand, category);

		product = productRepo.save(product);

		ProductDto response_dto = ProductUtility.converProductToProductDto(product);

		return response_dto;

	}

	@Override
	public Page<ProductDto> getAllProducts(Pageable pageable) {
		Page<ProductDto> products = productRepo.findAll(pageable).map(ProductUtility::converProductToProductDto);
		return products;

	}

	@Override
	public ProductDto getProductById(int id) {
		Optional<Product> product = productRepo.findById(id);
		ProductDto response_dto = null;
		if (product.isPresent()) {
			response_dto = ProductUtility.converProductToProductDto(product.get());

		}
		return response_dto;
	}

	
}
