package com.telusko.titans.pms.dto;


import java.math.BigDecimal;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

	private Integer productId;
	
	private String productName;
			 
	private Integer productPrice;
	
	private BigInteger quantityAvailable;
	
	private BigDecimal productRating;

	private byte[] productImage;
	
	private ProductCategoryDto productCategory;

	private ProductBrandDto productBrand;

}
