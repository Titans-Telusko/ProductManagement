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
@Table(name="product_category")
public class ProductCategory { 
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "category_seq")
	@SequenceGenerator(name = "category_seq",sequenceName = "category_seq",initialValue = 3000,allocationSize = 1)
	@Column(name="category_id")
	private Integer categoryId;
	
	@Column(name="category_name",nullable = false)
	private String categoryName;	
	
	@Column(name="category_occassion")
	private String occassion;
	
	@Column(name="category_consumer_type")
	private String consumerType;
	
	@OneToMany(mappedBy="productCategory" , cascade = CascadeType.ALL)
	private List<Product> products;
	
	

}
