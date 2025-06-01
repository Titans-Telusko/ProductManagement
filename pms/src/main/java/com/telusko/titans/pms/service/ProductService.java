package com.telusko.titans.pms.service;


import com.telusko.titans.pms.exceptions.BrandNameNotValidException;
import com.telusko.titans.pms.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
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

        return ProductUtility.converProductToProductDto(product);

	}

	@Override
	public ProductDto updateProduct(Integer id ,ProductDto requestDto) {
		Optional<Product> productOpt = productRepo.findById(id);
		if (productOpt.isEmpty()) {
			throw new ProductNotFoundException("Product not found with id: " + id);
		}
		Product existingProduct = productOpt.get();

		// Get or create brand
		ProductBrand brand = brandRepo.findByBrandNameIgnoreCase(requestDto.getProductBrand().getBrandName());
		if (brand == null) {
			brand = new ProductBrand();
			brand.setBrandName(requestDto.getProductBrand().getBrandName());
			brand = brandRepo.save(brand);
		}

		// Get or create category
		ProductCategory category = categoryRepo
				.findByCategoryNameIgnoreCase(requestDto.getProductCategory().getCategoryName());
		if (category == null) {
			category = new ProductCategory();
			category.setCategoryName(requestDto.getProductCategory().getCategoryName());
			category = categoryRepo.save(category);
		}

		// Update fields on the existing product
		existingProduct.setProductName(requestDto.getProductName());
		existingProduct.setProductPrice(requestDto.getProductPrice());
		existingProduct.setQuantityAvailable(requestDto.getQuantityAvailable());
		existingProduct.setProductRating(requestDto.getProductRating());

		if (requestDto.getProductImage() != null) {
			existingProduct.setProductImage(requestDto.getProductImage());
		}

		existingProduct.setProductBrand(brand);
		existingProduct.setProductCategory(category);

		// Save and return
		Product savedProduct = productRepo.save(existingProduct);
		return ProductUtility.converProductToProductDto(savedProduct);
    }

	@Override
	public Page<ProductDto> getAllProducts(Pageable pageable) {
		Page<ProductDto> products = productRepo.findAll(pageable).map(product -> ProductUtility.converProductToProductDto(product));
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

	@Override
	public Page<ProductDto> searchProductsByBrand(String brandName, Pageable pageable) {
		if (brandName == null || brandName.equals("null") || brandName.isEmpty()) {
			throw new BrandNameNotValidException("Brand cannot be null or empty");
		}
		Page<Product> products = brandRepo.findByBrandNameContainingIgnoreCase(brandName, pageable);
		if (products != null && !products.getContent().isEmpty()) {
			Page<ProductDto> responseProducts = products.map(product -> ProductUtility.converProductToProductDto(product));
			return responseProducts;
		}
		throw new BrandNameNotValidException("Brand does not exist or Not Valid Brand");
	}


}
