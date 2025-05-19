package com.telusko.titans.pms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.telusko.titans.pms.model.ProductCategory;

@Repository
public interface IProductCategoryRepo extends JpaRepository<ProductCategory, Integer> {

	ProductCategory findByCategoryNameIgnoreCase(String productCategory);

}
