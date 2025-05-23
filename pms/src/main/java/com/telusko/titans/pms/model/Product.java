package com.telusko.titans.pms.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
	@Column(name="product_id")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "product_sequence")
	@SequenceGenerator(name = "product_sequence",sequenceName = "product_sequence",allocationSize = 1,initialValue = 5000)
	private Integer productId;
	
	@Column(name="product_name",nullable = false)
	private String productName;
	
	@Column(name="product_price",nullable = false)
	private Integer productPrice;
	
	@Column(name="product_quantity_available")
	private BigInteger quantityAvailable;

	@Column(name="product_rating",precision = 10,scale = 2)
	private BigDecimal productRating;
	
	@Column(name="product_image")
	@Lob
	private byte[] productImage;		
	
	@ManyToOne
	@JoinColumn(name="product_category_id")
	private ProductCategory productCategory;
	
	@ManyToOne
	@JoinColumn(name="product_brand_id")
	private ProductBrand productBrand;

}
