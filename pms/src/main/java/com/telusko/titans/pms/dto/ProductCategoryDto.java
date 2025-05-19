package com.telusko.titans.pms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProductCategoryDto { 	
	
	private Integer categoryId;
	
	private String categoryName;	
	
	private String occassion;
	
	private String consumerType;	

}
