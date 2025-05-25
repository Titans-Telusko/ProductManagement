package com.telusko.titans.pms.utils;

import org.springframework.beans.BeanUtils;
import com.telusko.titans.pms.dto.ProductDto;
import com.telusko.titans.pms.dto.ProductBrandDto;
import com.telusko.titans.pms.dto.ProductCategoryDto;
import com.telusko.titans.pms.model.Product;
import com.telusko.titans.pms.model.ProductBrand;
import com.telusko.titans.pms.model.ProductCategory;

public class ProductUtility {

	/**
	 * convert ProductModelToDto, Copies the properties from Product to ProductDto *
	 **/
	public static ProductDto converProductToProductDto(Product product) {
		ProductDto productDto = new ProductDto();
		ProductBrandDto brandDto = new ProductBrandDto();
		ProductCategoryDto categoryDto = new ProductCategoryDto();

		BeanUtils.copyProperties(product.getProductBrand(), brandDto);
		BeanUtils.copyProperties(product.getProductCategory(), categoryDto);
		BeanUtils.copyProperties(product, productDto, "productCategory", "productBrand");
		
		productDto.setProductBrand(brandDto);
		productDto.setProductCategory(categoryDto);

		return productDto;

	}

	/**
	 * construct ProductModel, Creates Product from Product Dto *
	 **/
	public static Product constructProductModel(ProductDto dto, ProductBrand brand, ProductCategory category) {
		Product product = new Product();
		BeanUtils.copyProperties(dto, product, "productCategory", "productBrand");
		product.setProductBrand(brand);
		product.setProductCategory(category);
		return product;
	}

}
