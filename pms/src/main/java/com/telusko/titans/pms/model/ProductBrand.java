package com.telusko.titans.pms.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="product_brand")
public class ProductBrand {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "brand_seq")
	@SequenceGenerator(name = "brand_seq",sequenceName = "brand_seq",initialValue = 2000,allocationSize = 1)
	@Column(name="brand_id")
	private Integer brandId;
	
	@Column(name="brand_name",nullable = false)
	private String brandName;
	
	@OneToMany(mappedBy = "productBrand", cascade = CascadeType.ALL)
	private List<Product> products;
	
	
	
	

}
